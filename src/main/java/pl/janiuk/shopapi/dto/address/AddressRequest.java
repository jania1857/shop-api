package pl.janiuk.shopapi.dto.address;

public record AddressRequest(
        String city,
        String street,
        int address,
        Integer apartment,
        int zipCode
) {
}
