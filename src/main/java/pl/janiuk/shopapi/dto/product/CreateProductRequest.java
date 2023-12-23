package pl.janiuk.shopapi.dto.product;

public record CreateProductRequest(
        String name,
        String description,
        double price
) {
}
