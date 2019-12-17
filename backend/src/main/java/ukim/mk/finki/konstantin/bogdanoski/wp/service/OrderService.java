package ukim.mk.finki.konstantin.bogdanoski.wp.service;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface OrderService extends BaseEntityCrudService<PizzaOrder> {
    public List<PizzaOrder> findByPizza(Pizza pizza);
}
