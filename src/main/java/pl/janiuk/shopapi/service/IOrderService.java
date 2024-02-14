package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    void orderCart(int cartId, int addressId) throws Exception;
    List<Order> list();
    List<Order> clientOrders(int clientId);
    Optional<Order> single(int orderId);
}
