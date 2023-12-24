package pl.janiuk.shopapi.dto.changesinstock;

public record CreateChangesInStockRequest(
        int productId,
        boolean deliverySale,
        int change
) {
}
