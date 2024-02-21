package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.janiuk.shopapi.dto.address.AddressRequest;
import pl.janiuk.shopapi.dto.address.AddressResponse;

import java.util.List;

@RequestMapping("/api/v1/address")
public interface IAddressController {
    @PostMapping
    ResponseEntity<AddressResponse> addAddress(Authentication authentication, @RequestBody AddressRequest request);
    @DeleteMapping("/{addressId}")
    ResponseEntity<?> deleteAddress(@PathVariable int addressId, Authentication authentication);
    @GetMapping("/{clientId}")
    ResponseEntity<List<AddressResponse>> clientAddresses(@PathVariable int clientId);
    @GetMapping
    ResponseEntity<List<AddressResponse>> ownedAddresses(Authentication authentication);

}
