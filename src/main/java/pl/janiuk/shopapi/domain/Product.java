package pl.janiuk.shopapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "availability")
    private Integer availability;
    @Basic
    @Column(name = "price")
    private Double price;
    @OneToMany(mappedBy = "productByProductId", cascade = CascadeType.ALL)
    private Collection<ChangesInStock> changesInStocksByProductId;
    @OneToMany(mappedBy = "productByProductId", cascade = CascadeType.ALL)
    private Collection<ProductCategories> productCategoriesByProductId;
    @OneToMany(mappedBy = "productByProductId", cascade = CascadeType.ALL)
    private Collection<ProductInCart> productInCartsByProductId;
}
