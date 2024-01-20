package pl.janiuk.shopapi.dto.client;

public record ClientResponse(
        int id,
        String username,
        String firstname,
        String lastname,
        String role
) {
}
