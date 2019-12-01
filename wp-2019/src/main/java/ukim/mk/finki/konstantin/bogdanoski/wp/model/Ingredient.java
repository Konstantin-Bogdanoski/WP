package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class Ingredient extends BaseEntity {
    private String name;
    private boolean spicy;
    private float amount;
    private boolean veggie;
    @ManyToOne
    private Pizza pizza;
}
