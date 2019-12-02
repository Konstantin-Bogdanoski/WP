package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class PizzaOrder extends BaseEntity {
    @OneToOne
    @JoinColumn
    private Pizza pizza;

    private String size;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private String address;
}
