package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PizzaIngredient {
    @EmbeddedId
    PizzaIngredientCompositeKey compositeKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("pizza_id")
    @JoinColumn(name = "pizza_id")
    Pizza pizza;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("ingredient_id")
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    float amount;
}
