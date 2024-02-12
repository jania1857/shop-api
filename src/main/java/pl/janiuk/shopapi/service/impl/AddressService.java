package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.Address;
import pl.janiuk.shopapi.repository.AddressRepository;
import pl.janiuk.shopapi.service.IAddressService;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(int clientId, String city, String street, int address, int apartment, int zipCode) {
        Address newAddress = Address.builder()
                .clientId(clientId)
                .city(city)
                .street(street)
                .address(address)
                .apartment(apartment)
                .zipCode(zipCode)
                .build();
        return addressRepository.save(newAddress);
    }

    @Override
    public Address create(int clientId, String city, String street, int address, int zipCode) {
        Address newAddress = Address.builder()
                .clientId(clientId)
                .city(city)
                .street(street)
                .address(address)
                .zipCode(zipCode)
                .build();
        return addressRepository.save(newAddress);
    }

    @Override
    public void delete(int addressId) {
        if(addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
        }
    }

    @Override
    public Optional<Address> single(int addressId) {
        return addressRepository.findById(addressId);
    }

    @Override
    public List<Address> clientAddresses(int clientId) {
        return addressRepository.findAddressByClientId(clientId);
    }
}
