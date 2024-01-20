package pl.janiuk.shopapi.dto.client;

public record RegisterRequest(
        String username,
        String password,
        String firstname,
        String lastname
) {
}
