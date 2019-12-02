package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class Ingredient extends BaseEntity implements Comparable<Ingredient> {
    private String name;
    private boolean spicy;
    private boolean veggie;
    @OneToMany(mappedBy = "ingredient")
    private List<PizzaIngredient> pizzaIngredients;

    @Override
    public int compareTo(Ingredient ingredient) {
        return this.name.compareTo(ingredient.getName());
    }
}
