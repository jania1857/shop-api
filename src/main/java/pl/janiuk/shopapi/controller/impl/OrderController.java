package pl.janiuk.shopapi.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.IOrderController;
import pl.janiuk.shopapi.domain.*;
import pl.janiuk.shopapi.dto.address.AddressResponse;
import pl.janiuk.shopapi.dto.cart.ProductInCartRecord;
import pl.janiuk.shopapi.dto.client.details.ClientData;
import pl.janiuk.shopapi.dto.order.OrderResponse;
import pl.janiuk.shopapi.exception.CartDoesNotExistException;
import pl.janiuk.shopapi.service.ICartService;
import pl.janiuk.shopapi.service.IClientService;
import pl.janiuk.shopapi.service.IOrderService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class OrderController implements IOrderController {
    private final IClientService clientService;
    private final ICartService cartService;
    private final IOrderService orderService;

    public OrderController(IClientService clientService, ICartService cartService, IOrderService orderService) {
        this.clientService = clientService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderResponse> orderCart(Authentication authentication, int addressId) {
        try {
            int cartId = getCartId(authentication);
            orderService.orderCart(cartId, addressId);
            cartService.create(getClientId(authentication));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<List<OrderResponse>> ownedOrders(Authentication authentication) {
        int clientId = getClientId(authentication);
        List<Order> orderList = orderService.clientOrders(clientId);

        List<OrderResponse> response = orderList.stream().map(order -> {
            try {
                return orderToOrderResponse(order);
            } catch (Exception e) {
                return null;
            }
        }).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<OrderResponse>> clientOrders(int clientId) {
        List<Order> orderList = orderService.clientOrders(clientId);

        List<OrderResponse> response = orderList.stream().map(order -> {
            try {
                return orderToOrderResponse(order);
            } catch (Exception e) {
                return null;
            }
        }).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderResponse>> listOrders() {
        List<Order> orderList = orderService.list();
        List<OrderResponse> response = orderList.stream().map(order -> {
            try {
                return orderToOrderResponse(order);
            } catch (Exception e) {
                return null;
            }
        }).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponse> singleOrder(int orderId) {
        Optional<Order> foundOrder = orderService.single(orderId);
        if(foundOrder.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Order order = foundOrder.get();
        try {
            OrderResponse response = orderToOrderResponse(order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private int getClientId(Authentication authentication) {
        String username = authentication.getName();
        return clientService.findByUsername(username).getId();
    }

    private int getCartId(Authentication authentication) throws Exception {
        int clientId = getClientId(authentication);
        List<Cart> cartList = cartService.clientCarts(clientId);
        Optional<Cart> foundNotOrderedCart = cartList.stream()
                .filter(obj -> !obj.isOrdered())
                .findFirst();
        if(foundNotOrderedCart.isEmpty()) {
            throw new CartDoesNotExistException();
        }
        Cart notOrderedCart = foundNotOrderedCart.get();
        return notOrderedCart.getId();
    }

    private List<ProductInCartRecord> productInCartCollectionToProductInCartRecordList(Collection<ProductInCart> productInCartCollection) {
        List<ProductInCart> productInCartList = new ArrayList<>(productInCartCollection);
        return productInCartList.stream().map(this::productInCartToProductInCartRecord).collect(Collectors.toList());
    }

    private ProductInCartRecord productInCartToProductInCartRecord(ProductInCart productInCart) {
        return new ProductInCartRecord(
                productInCart.getProductId(),
                productInCart.getQuantity()
        );
    }

    private OrderResponse orderToOrderResponse(Order order) throws Exception {
        Optional<Cart> cart = cartService.single(order.getCartId());
        if (cart.isEmpty()) {
            throw new Exception();
        }
        Collection<ProductInCart> productsInCart = cart.get().getProductInCartsByCartId();
        List<ProductInCartRecord> productInCartRecords = productInCartCollectionToProductInCartRecordList(productsInCart);
        return new OrderResponse(
                order.getId(),
                order.getCartId(),
                order.getAddressId(),
                productInCartRecords,
                getOrderStatus(order),
                getOrderAddress(order),
                getOrderClient(order)
        );
    }

    private String getOrderStatus(Order order) {
        ChangesInStatus newestChange = order.getChangesInStatusesByOrderId().stream().max(Comparator.comparingInt(ChangesInStatus::getId)).orElseThrow();
        return newestChange.getStatusByStatusId().getName();
    }

    private AddressResponse getOrderAddress(Order order) {
        Address address = order.getAddressByAddressId();
        return addressToAddressResponse(address);
    }

    private AddressResponse addressToAddressResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getClientId(),
                address.getCity(),
                address.getStreet(),
                address.getAddress(),
                address.getApartment(),
                address.getZipCode()
        );
    }

    private ClientData getOrderClient(Order order) {
        Client client = order.getCartByCartId().getClientByClientId();
        return new ClientData(
                client.getFirstname(),
                client.getLastname()
        );
    }
}
