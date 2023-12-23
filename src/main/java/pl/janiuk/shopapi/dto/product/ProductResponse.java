package pl.janiuk.shopapi.dto.product;

import pl.janiuk.shopapi.dto.category.CategoryResponse;

import java.util.List;

public record ProductResponse(
        int id,
        String name,
        String description,
        int availability,
        double price,
        List<CategoryResponse> categories
) {
}
