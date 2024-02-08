package pl.janiuk.shopapi.dto.cart;

public record CartRequest(
        int productId,
        int quantity
) {
}
