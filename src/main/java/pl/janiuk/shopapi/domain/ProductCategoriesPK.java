package pl.janiuk.shopapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCategoriesPK implements Serializable {
    @Column(name = "category_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    @Column(name = "product_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
}
