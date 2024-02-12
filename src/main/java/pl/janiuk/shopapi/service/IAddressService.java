package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressService {
    Address create(int clientId, String city, String street, int address, int apartment, int zipCode);
    Address create(int clientId, String city, String street, int address, int zipCode);
    void delete(int addressId);
    Optional<Address> single(int addressId);
    List<Address> clientAddresses(int clientId);
}
