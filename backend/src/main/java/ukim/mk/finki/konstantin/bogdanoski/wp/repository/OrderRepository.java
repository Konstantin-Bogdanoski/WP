package ukim.mk.finki.konstantin.bogdanoski.wp.repository;

import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface OrderRepository extends JpaSpecificationRepository<PizzaOrder> {
}
