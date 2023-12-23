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
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private int id;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "street")
    private String street;
    @Basic
    @Column(name = "address")
    private Integer address;
    @Basic
    @Column(name = "apartment")
    private Integer apartment;
    @Basic
    @Column(name = "zip_code")
    private Integer zipCode;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Client clientByClientId;
    @OneToMany(mappedBy = "addressByAddressId")
    private Collection<Order> ordersByAddressId;
}
