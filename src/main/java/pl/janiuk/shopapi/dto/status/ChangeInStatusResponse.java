package pl.janiuk.shopapi.dto.status;

import java.sql.Date;

public record ChangeInStatusResponse(
        int changeId,
        Date date,
        int orderId,
        String status
) {
}
