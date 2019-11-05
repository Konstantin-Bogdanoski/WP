package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class PizzaOrder extends BaseEntity {
    @OneToOne
    private Pizza pizza;
    private String size;
    @OneToOne
    private User user;
    private String address;
}
