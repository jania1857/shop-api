package pl.janiuk.shopapi.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.IAddressController;
import pl.janiuk.shopapi.domain.Address;
import pl.janiuk.shopapi.dto.address.AddressRequest;
import pl.janiuk.shopapi.dto.address.AddressResponse;
import pl.janiuk.shopapi.service.IAddressService;
import pl.janiuk.shopapi.service.IClientService;

import java.util.List;
import java.util.Objects;

@RestController
public class AddressController implements IAddressController {
    private final IAddressService addressService;
    private final IClientService clientService;

    @Autowired
    public AddressController(IAddressService addressService, IClientService clientService) {
        this.addressService = addressService;
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<AddressResponse> addAddress(Authentication authentication, AddressRequest request) {
        int clientId = getClientId(authentication);
        Address savedAddress;
        if(request.apartment() == null) {
            savedAddress = addressService.create(clientId, request.city(), request.street(), request.address(),
                    request.zipCode());
        } else {
            savedAddress = addressService.create(clientId, request.city(), request.street(), request.address(),
                    request.apartment(), request.zipCode());
        }
        AddressResponse response = addressToAddressResponse(savedAddress);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteAddress(int addressId, Authentication authentication) {
        int clientId = getClientId(authentication);
        List<Address> addressList = addressService.clientAddresses(clientId);
        Address addressToDelete = addressList.stream()
                .filter(obj -> Objects.equals(obj.getId(), addressId))
                .findFirst()
                .orElse(null);
        if(!(addressToDelete == null)) {
            addressService.delete(addressId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<AddressResponse>> clientAddresses(int clientId) {
        if (!clientService.existsById(clientId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<Address> clientAddresses = addressService.clientAddresses(clientId);
        List<AddressResponse> response =
                clientAddresses.stream().map(this::addressToAddressResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AddressResponse>> ownedAddresses(Authentication authentication) {
        int clientId = getClientId(authentication);
        List<Address> clientAddresses = addressService.clientAddresses(clientId);
        List<AddressResponse> response =
                clientAddresses.stream().map(this::addressToAddressResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    int getClientId(Authentication authentication) {
        return clientService.findByUsername(authentication.getName()).getId();
    }

    AddressResponse addressToAddressResponse(Address address) {
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
}
