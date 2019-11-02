package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class Order extends BaseEntity {
    private Pizza pizza;
    private User user;
}
