package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiuk.shopapi.domain.ProductInCart;
import pl.janiuk.shopapi.domain.ProductInCartPK;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, ProductInCartPK> {
}
