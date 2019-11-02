package ukim.mk.finki.konstantin.bogdanoski.wp.model.user;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Order;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Getter
@Setter
public class User extends BaseEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<Order> orderList;

    public User() {
        orderList = new ArrayList<>();
    }

    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
