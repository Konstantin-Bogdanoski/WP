package ukim.mk.finki.konstantin.bogdanoski.wp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface PizzaService extends BaseEntityCrudService<Pizza> {
    public Pizza findByName(String name);

    public Page<Pizza> findPaginated(Pageable pageable);
}
