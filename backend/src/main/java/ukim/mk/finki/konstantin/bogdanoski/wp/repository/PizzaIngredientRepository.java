package ukim.mk.finki.konstantin.bogdanoski.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredientCompositeKey;

import javax.transaction.Transactional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface PizzaIngredientRepository extends JpaRepository<PizzaIngredient, PizzaIngredientCompositeKey> {
    @Transactional
    @Modifying
    void deletePizzaIngredientByPizza(Pizza pizza);
}
