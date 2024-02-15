package pl.janiuk.shopapi.dto.status;

public record ChangeInStatusRequest(
        int orderId,
        int statusId
) {
}
