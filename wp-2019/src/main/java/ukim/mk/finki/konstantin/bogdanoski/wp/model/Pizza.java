package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class Pizza extends BaseEntity {
    private String name;
    private String description;
    private boolean veggie;
    @OneToMany(mappedBy = "pizza")
    private List<PizzaIngredient> pizzaIngredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return veggie == pizza.veggie &&
                Objects.equals(name, pizza.name) &&
                Objects.equals(description, pizza.description) &&
                Objects.equals(pizzaIngredients, pizza.pizzaIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, veggie, pizzaIngredients);
    }
}
