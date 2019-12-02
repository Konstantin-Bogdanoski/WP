package ukim.mk.finki.konstantin.bogdanoski.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredientCompositeKey;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface PizzaIngredientRepository extends JpaRepository<PizzaIngredient, PizzaIngredientCompositeKey> {
}
