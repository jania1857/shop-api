package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiuk.shopapi.domain.ChangesInStatus;

import java.util.List;

public interface ChangesInStatusRepository extends JpaRepository<ChangesInStatus, Integer> {
    List<ChangesInStatus> findChangesInStatusByOrderId(int orderId);
}
