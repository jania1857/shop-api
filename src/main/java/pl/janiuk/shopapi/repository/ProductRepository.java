package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiuk.shopapi.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
