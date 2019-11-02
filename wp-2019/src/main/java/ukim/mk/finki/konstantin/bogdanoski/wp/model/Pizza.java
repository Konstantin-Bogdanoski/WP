package ukim.mk.finki.konstantin.bogdanoski.wp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class Pizza extends BaseEntity {
    private String name;
    private String description;
}
