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
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private int id;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @Basic
    @Column(name = "ordered")
    private boolean ordered;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Client clientByClientId;
    @OneToMany(mappedBy = "cartByCartId")
    private Collection<Order> ordersByCartId;
    @OneToMany(mappedBy = "cartByCartId")
    private Collection<ProductInCart> productInCartsByCartId;
}
