package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaOrder that = (PizzaOrder) o;
        return Objects.equals(pizza, that.pizza) &&
                Objects.equals(size, that.size) &&
                Objects.equals(user, that.user) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizza, size, user, address);
    }
}
