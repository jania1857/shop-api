package pl.janiuk.shopapi.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInCartPK implements Serializable {
    @Column(name = "cart_id")
    private int cartId;
    @Column(name = "product_id")
    private int productId;
}
