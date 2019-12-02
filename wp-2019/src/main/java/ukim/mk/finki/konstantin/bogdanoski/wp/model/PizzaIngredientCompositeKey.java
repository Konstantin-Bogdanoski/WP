package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaIngredientCompositeKey implements Serializable {
    @Column(name = "pizza_id")
    Long pizzaId;

    @Column(name = "ingredient_id")
    Long ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaIngredientCompositeKey that = (PizzaIngredientCompositeKey) o;
        return pizzaId.equals(that.pizzaId) &&
                ingredientId.equals(that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizzaId, ingredientId);
    }
}
