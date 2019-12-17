package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.OrderService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping(path = "/orders", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private OrderService orderService;
    private PizzaService pizzaService;

    public OrderController(OrderService orderService, PizzaService pizzaService) {
        this.orderService = orderService;
        this.pizzaService = pizzaService;
    }

    @GetMapping("/pizzas")
    public Map<String, Long> getOrders() {
        Map<String, Long> orders = new HashMap<>();
        pizzaService.findAll().forEach(pizza -> orders.put(pizza.getName(), (long) orderService.findByPizza(pizza).size()));
        return orders;
    }
}