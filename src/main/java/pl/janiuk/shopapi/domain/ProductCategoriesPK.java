package pl.janiuk.shopapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoriesPK implements Serializable {
    @Column(name = "category_id")
    @Id
    private int categoryId;
    @Column(name = "product_id")
    @Id
    private int productId;
}
