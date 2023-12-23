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
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private int id;
    @Basic
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<Client> clientsByRoleId;
}
