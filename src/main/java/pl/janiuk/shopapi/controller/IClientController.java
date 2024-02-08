package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.janiuk.shopapi.dto.client.ClientResponse;
import pl.janiuk.shopapi.dto.client.RegisterRequest;

import java.util.List;

@RequestMapping("/api/v1/client")
public interface IClientController {
    @PostMapping("/register")
    ResponseEntity<ClientResponse> register(@RequestBody RegisterRequest request);
    @GetMapping
    ResponseEntity<List<ClientResponse>> listClients();
    @GetMapping("/{clientId}")
    ResponseEntity<ClientResponse> singleClient(@PathVariable int clientId);
    @PatchMapping("/{clientId}/{roleId}")
    ResponseEntity<ClientResponse> changeClientRole(@PathVariable int clientId, @PathVariable int roleId);
}
