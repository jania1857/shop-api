package pl.janiuk.shopapi.service.impl;

import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.*;
import pl.janiuk.shopapi.repository.AddressRepository;
import pl.janiuk.shopapi.repository.CartRepository;
import pl.janiuk.shopapi.repository.ChangesInStatusRepository;
import pl.janiuk.shopapi.repository.OrderRepository;
import pl.janiuk.shopapi.service.IChangesInStockService;
import pl.janiuk.shopapi.service.IOrderService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final ChangesInStatusRepository changesInStatusRepository;
    private final IChangesInStockService changesInStockService;

    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, AddressRepository addressRepository, ChangesInStatusRepository changesInStatusRepository, IChangesInStockService changesInStockService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.changesInStatusRepository = changesInStatusRepository;
        this.changesInStockService = changesInStockService;
    }

    @Override
    public void orderCart(int cartId, int addressId) throws Exception {
        Optional<Cart> foundCart = cartRepository.findById(cartId);
        if (foundCart.isEmpty() || !addressRepository.existsById(addressId)) {
            throw new Exception();
        }
        Date now = Date.valueOf(LocalDateTime.now().toLocalDate());
        Cart cart = foundCart.get();

        List<ProductInCart> productsInCart = cart.getProductInCartsByCartId().stream().toList();
        List<ChangesInStock> changes = productsInCart.stream().map(productInCart -> {
            int productId = productInCart.getProductId();
            int quantity = productInCart.getQuantity();
            return changesInStockService.create(productId, false, quantity);
        }).toList();


        Order order = Order.builder()
                .orderDate(now)
                .cartId(cartId)
                .addressId(addressId)
                .build();
        cart.setOrdered(true);
        Order savedOrder = orderRepository.save(order);
        ChangesInStatus changeInStatus = ChangesInStatus.builder()
                .statusId(1)
                .orderId(savedOrder.getId())
                .changeDate(now)
                .build();
        changesInStatusRepository.save(changeInStatus);
        cartRepository.save(cart);
        savedOrder.setChangesInStatusesByOrderId(List.of(changeInStatus));
    }

    @Override
    public List<Order> list() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> clientOrders(int clientId) {
        List<Cart> clientCarts = cartRepository.findCartByClientId(clientId);
        return clientCarts.stream()
                .filter(Cart::isOrdered)
                .map(cart -> {
                    int cartId = cart.getId();
                    return orderRepository.findOrderByCartId(cartId);
                }).toList();
    }

    @Override
    public Optional<Order> single(int orderId) {
        return orderRepository.findById(orderId);
    }
}
