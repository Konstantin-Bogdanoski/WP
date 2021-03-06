package ukim.mk.finki.konstantin.bogdanoski.wp.model.user;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Getter
@Setter
@Entity
@Table(name = "pizza_user")
public class User extends BaseEntity implements Comparable<User> {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String userRole;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<PizzaOrder> orderList;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public int compareTo(User user) {
        return this.getId().compareTo(user.getId());
    }
}
