package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janiuk.shopapi.dto.order.OrderResponse;

import java.util.List;

@RequestMapping("/api/v1/order")
public interface IOrderController {
    @PostMapping("/{addressId}")
    ResponseEntity<OrderResponse> orderCart(Authentication authentication, @PathVariable int addressId);
    @GetMapping("/owned")
    ResponseEntity<List<OrderResponse>> ownedOrders(Authentication authentication);
    @GetMapping("/client/{clientId}")
    ResponseEntity<List<OrderResponse>> clientOrders(@PathVariable int clientId);
    @GetMapping
    ResponseEntity<List<OrderResponse>> listOrders();
    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponse> singleOrder(@PathVariable int orderId);
}
