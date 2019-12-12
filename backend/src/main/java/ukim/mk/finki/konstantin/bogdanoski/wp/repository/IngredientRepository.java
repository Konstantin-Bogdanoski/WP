package ukim.mk.finki.konstantin.bogdanoski.wp.repository;

import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface IngredientRepository extends JpaSpecificationRepository<Ingredient> {
}
