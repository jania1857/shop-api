package pl.janiuk.shopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiuk.shopapi.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderByCartId(int cartId);
}
