package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.janiuk.shopapi.dto.cart.CartRequest;
import pl.janiuk.shopapi.dto.cart.CartResponse;
import pl.janiuk.shopapi.dto.cart.RemoveProductRequest;

import java.util.List;

@RequestMapping("/api/v1/cart")
public interface ICartController {
    @GetMapping("/{cartId}")
    ResponseEntity<CartResponse> singleCart(@PathVariable int cartId);
    @GetMapping("/admin/client/{clientId}")
    ResponseEntity<List<CartResponse>> clientCarts(@PathVariable int clientId);
    @GetMapping("/owned")
    ResponseEntity<List<CartResponse>> ownedCarts(Authentication authentication);
    @GetMapping
    ResponseEntity<List<CartResponse>> listCarts();
    @PostMapping
    ResponseEntity<CartResponse> addProductToCart(Authentication authentication, @RequestBody CartRequest request);
    @DeleteMapping
    ResponseEntity<CartResponse> removeProductFromCart(Authentication authentication, @RequestBody RemoveProductRequest request);
    @PatchMapping
    ResponseEntity<CartResponse> changeProductQuantity(Authentication authentication, @RequestBody CartRequest request);
}
