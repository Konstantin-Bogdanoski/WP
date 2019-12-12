package ukim.mk.finki.konstantin.bogdanoski.wp.repository;

import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface PizzaRepository extends JpaSpecificationRepository<Pizza> {
    Optional<Pizza> findByName(String name);
}