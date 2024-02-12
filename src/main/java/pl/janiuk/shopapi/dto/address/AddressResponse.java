package pl.janiuk.shopapi.dto.address;

public record AddressResponse(
        int addressId,
        int clientId,
        String city,
        String street,
        int address,
        Integer apartment,
        int zipCode
) {
}
