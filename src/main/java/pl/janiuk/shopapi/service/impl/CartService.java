package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.Cart;
import pl.janiuk.shopapi.domain.ProductInCart;
import pl.janiuk.shopapi.domain.ProductInCartPK;
import pl.janiuk.shopapi.exception.*;
import pl.janiuk.shopapi.repository.CartRepository;
import pl.janiuk.shopapi.repository.ProductInCartRepository;
import pl.janiuk.shopapi.repository.ProductRepository;
import pl.janiuk.shopapi.service.ICartService;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductInCartRepository productInCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductInCartRepository productInCartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productInCartRepository = productInCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void create(int clientId) {
        List<Cart> clientCarts = clientCarts(clientId);
        boolean isCartNotOrdered = clientCarts.stream().anyMatch(cart -> !cart.isOrdered());
        if (!isCartNotOrdered) {
            Cart cart = Cart.builder()
                    .clientId(clientId)
                    .build();
            cartRepository.save(cart);
        }
    }

    @Override
    public Optional<Cart> single(int cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public List<Cart> clientCarts(int clientId) {
        return cartRepository.findCartByClientId(clientId);
    }

    @Override
    public List<Cart> list() {
        return cartRepository.findAll();
    }

    @Override
    public Cart addProduct(int cartId, int productId, int quantity) throws CartDoesNotExistException,
            ProductDoesNotExistException, CartAlreadyOrderedException, InternalErrorException {
        Optional<Cart> foundCart = cartRepository.findById(cartId);
        if (foundCart.isEmpty()) {
            throw new CartDoesNotExistException();
        }
        if(!productRepository.existsById(productId)) {
            throw new ProductDoesNotExistException();
        }
        Cart cart = foundCart.get();
        if (cart.isOrdered()) {
            throw new CartAlreadyOrderedException();
        }
        ProductInCartPK productInCartPK = ProductInCartPK.builder()
                .productId(productId)
                .cartId(cartId)
                .build();
        if (productInCartRepository.existsById(productInCartPK)) {
            Optional<ProductInCart> foundProductInCart = productInCartRepository.findById(productInCartPK);
            if (foundProductInCart.isEmpty()) {
                throw new InternalErrorException();
            }
            ProductInCart productInCart = foundProductInCart.get();
            int actualOrderedQuantity = productInCart.getQuantity();
            int newOrderedQuantity = actualOrderedQuantity + quantity;
            productInCart.setQuantity(newOrderedQuantity);
            ProductInCart savedProductInCart = productInCartRepository.save(productInCart);
            return savedProductInCart.getCartByCartId();
        } else {
            ProductInCart productInCart = ProductInCart.builder()
                    .productId(productId)
                    .cartId(cartId)
                    .quantity(quantity)
                    .build();
            productInCartRepository.save(productInCart);
            foundCart = cartRepository.findById(cartId);
            if (foundCart.isEmpty()) {
                throw new InternalErrorException();
            }
            cart = foundCart.get();
            return cart;
        }
    }

    @Override
    public Cart removeProduct(int cartId, int productId) throws CartDoesNotExistException {
        Optional<Cart> foundCart = cartRepository.findById(cartId);
        if(foundCart.isEmpty()) {
            throw new CartDoesNotExistException();
        }
        ProductInCartPK productInCartPK = ProductInCartPK.builder()
                .cartId(cartId)
                .productId(productId)
                .build();
        productInCartRepository.deleteById(productInCartPK);
        Optional<Cart> foundCartAgain = cartRepository.findById(cartId);
        if(foundCartAgain.isEmpty()) {
            throw new CartDoesNotExistException();
        }
        return foundCartAgain.get();
    }

    @Override
    public Cart changeProductQuantity(int cartId, int productId, int quantity) throws Exception {
        if(!cartRepository.existsById(cartId)) {
            throw new CartDoesNotExistException();
        }
        ProductInCartPK productInCartPK = ProductInCartPK.builder()
                .cartId(cartId)
                .productId(productId)
                .build();
        Optional<ProductInCart> foundProductInCart = productInCartRepository.findById(productInCartPK);
        if(foundProductInCart.isEmpty()) {
            throw new ProductInCartDoesNotExistException();
        }
        ProductInCart productInCart = foundProductInCart.get();
        productInCart.setQuantity(quantity);
        productInCartRepository.save(productInCart);
        Optional<Cart> foundCart = cartRepository.findById(cartId);
        if (foundCart.isEmpty()) {
            throw new InternalErrorException();
        }
        return foundCart.get();
    }
}


