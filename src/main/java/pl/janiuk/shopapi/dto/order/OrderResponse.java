package pl.janiuk.shopapi.dto.order;

import pl.janiuk.shopapi.dto.address.AddressResponse;
import pl.janiuk.shopapi.dto.cart.ProductInCartRecord;
import pl.janiuk.shopapi.dto.client.details.ClientData;

import java.util.List;

public record OrderResponse(
        int orderId,
        int cartId,
        int addressId,
        List<ProductInCartRecord> contents,
        String status,
        AddressResponse address,
        ClientData client
) {
}
