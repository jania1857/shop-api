package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiuk.shopapi.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
