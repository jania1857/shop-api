package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiuk.shopapi.domain.ProductCategories;
import pl.janiuk.shopapi.domain.ProductCategoriesPK;

public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, ProductCategoriesPK> {
}
