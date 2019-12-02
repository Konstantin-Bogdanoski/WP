package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

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
}
