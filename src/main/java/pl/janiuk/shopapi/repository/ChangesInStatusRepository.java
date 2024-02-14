package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiuk.shopapi.domain.ChangesInStatus;

public interface ChangesInStatusRepository extends JpaRepository<ChangesInStatus, Integer> {
}
