package pl.janiuk.shopapi.dto.changesinstock;

import java.sql.Date;

public record ChangesInStockResponse(
       int id,
       int productId,
       Date changeDate,
       boolean deliverySale,
       int change
) {
}
