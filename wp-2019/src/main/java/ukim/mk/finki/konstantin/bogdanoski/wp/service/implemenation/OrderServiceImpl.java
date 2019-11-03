package ukim.mk.finki.konstantin.bogdanoski.wp.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.repository.OrderRepository;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.OrderService;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class OrderServiceImpl extends BaseEntityCrudServiceImpl<PizzaOrder, OrderRepository> implements OrderService {

    private OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OrderRepository getRepository() {
        return repository;
    }

    public void placeOrder(Pizza pizza, User user) {

    }
}
