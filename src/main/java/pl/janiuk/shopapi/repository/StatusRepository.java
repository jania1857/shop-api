package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiuk.shopapi.domain.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
