package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiuk.shopapi.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByUsername(String username);
    Client findClientByUsername(String username);
}
