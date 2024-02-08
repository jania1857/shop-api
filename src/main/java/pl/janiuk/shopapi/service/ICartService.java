package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Cart;
import pl.janiuk.shopapi.exception.CartDoesNotExistException;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    void create(int clientId);
    Optional<Cart> single(int cartId); //GET
    List<Cart> clientCarts(int clientId); //GET
    List<Cart> list(); //GET
    Cart addProduct(int cartId, int productId, int quantity) throws Exception; //POST
    Cart removeProduct(int cartId, int productId) throws CartDoesNotExistException; //DELETE
    Cart changeProductQuantity(int cartId, int productId, int quantity) throws Exception; //PATCH
}
