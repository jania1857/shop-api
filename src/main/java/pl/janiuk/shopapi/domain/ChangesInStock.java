package pl.janiuk.shopapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "changes_in_stock", schema = "public", catalog = "shop_db")
public class ChangesInStock {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private int id;
    @Basic
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Column(name = "change_date")
    private Date changeDate;
    @Basic
    @Column(name = "delivery_sale")
    private boolean deliverySale;
    @Basic
    @Column(name = "change")
    private int change;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Product productByProductId;
    @PrePersist
    public void prePersis() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.changeDate = Date.valueOf(localDateTime.toLocalDate());
    }
}
