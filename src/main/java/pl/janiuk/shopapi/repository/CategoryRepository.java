package pl.janiuk.shopapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiuk.shopapi.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
