package ukim.mk.finki.konstantin.bogdanoski.wp.service;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface PizzaService extends BaseEntityCrudService<Pizza> {
    public Pizza findByName(String name);
}
