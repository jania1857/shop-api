package pl.janiuk.shopapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_in_cart", schema = "public", catalog = "shop_db")
@IdClass(ProductInCartPK.class)
public class ProductInCart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cart_id", insertable = false, updatable = false)
    private int cartId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Cart cartByCartId;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Product productByProductId;
}
