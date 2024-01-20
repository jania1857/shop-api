package pl.janiuk.shopapi.dto.client;

public record LoginRequest(
        String username,
        String password
) {
}
