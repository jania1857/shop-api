package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiuk.shopapi.domain.ChangesInStock;

import java.util.List;

@Repository
public interface ChangesInStockRepository extends JpaRepository<ChangesInStock, Integer> {
    List<ChangesInStock> findChangesInStockByProductId(int productId);
}
