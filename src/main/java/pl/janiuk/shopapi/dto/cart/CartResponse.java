package pl.janiuk.shopapi.dto.cart;

import java.util.List;

public record CartResponse(
        int cartId,
        int clientId,
        boolean ordered,
        List<ProductInCartRecord> productsInCart
) {
}
