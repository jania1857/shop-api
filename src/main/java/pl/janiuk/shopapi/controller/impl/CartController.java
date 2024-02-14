package pl.janiuk.shopapi.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.ICartController;
import pl.janiuk.shopapi.domain.Cart;
import pl.janiuk.shopapi.domain.ProductInCart;
import pl.janiuk.shopapi.dto.cart.CartRequest;
import pl.janiuk.shopapi.dto.cart.CartResponse;
import pl.janiuk.shopapi.dto.cart.ProductInCartRecord;
import pl.janiuk.shopapi.dto.cart.RemoveProductRequest;
import pl.janiuk.shopapi.exception.CartDoesNotExistException;
import pl.janiuk.shopapi.service.ICartService;
import pl.janiuk.shopapi.service.IClientService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CartController implements ICartController {
    private final ICartService cartService;
    private final IClientService clientService;

    public CartController(ICartService cartService, IClientService clientService) {
        this.cartService = cartService;
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<CartResponse> singleCart(int cartId) {
        Optional<Cart> foundCart = cartService.single(cartId);
        if (foundCart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Cart cart = foundCart.get();
        CartResponse response = cartToCartResponse(cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CartResponse>> clientCarts(int clientId) {
        List<Cart> cartList = cartService.clientCarts(clientId);
        List<CartResponse> response = cartList.stream().map(this::cartToCartResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CartResponse>> ownedCarts(Authentication authentication) {
        String username = authentication.getName();
        int clientId = clientService.findByUsername(username).getId();
        List<Cart> cartList = cartService.clientCarts(clientId);
        List<CartResponse> response = cartList.stream().map(this::cartToCartResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CartResponse>> listCarts() {
        List<Cart> cartList = cartService.list();
        List<CartResponse> response = cartList.stream().map(this::cartToCartResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartResponse> addProductToCart(Authentication authentication, CartRequest request) {
        try {
            int cartId = getCartId(authentication);
            try {
                Cart savedCart = cartService.addProduct(cartId, request.productId(), request.quantity());
                CartResponse response = cartToCartResponse(savedCart);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<CartResponse> removeProductFromCart(Authentication authentication, RemoveProductRequest request) {
        try {
            int cartId = getCartId(authentication);
            try {
                Cart savedCart = cartService.removeProduct(cartId, request.productId());
                CartResponse response = cartToCartResponse(savedCart);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<CartResponse> changeProductQuantity(Authentication authentication, CartRequest request) {
        try {
            int cartId = getCartId(authentication);
            try {
                Cart savedCart = cartService.changeProductQuantity(cartId, request.productId(), request.quantity());
                CartResponse response = cartToCartResponse(savedCart);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<CartResponse> getActiveCart(Authentication authentication) {
        try {
            int cartId = getCartId(authentication);
            Optional<Cart> foundCart = cartService.single(cartId);
            if (foundCart.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            CartResponse response = cartToCartResponse(foundCart.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private CartResponse cartToCartResponse(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getClientId(),
                cart.isOrdered(),
                productInCartCollectionToProductInCartRecordList(cart.getProductInCartsByCartId())
        );
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

    private int getCartId(Authentication authentication) throws Exception {
        String username = authentication.getName();
        int clientId = clientService.findByUsername(username).getId();
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
}
