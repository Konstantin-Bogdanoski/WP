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
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping(path = "/orders", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private OrderService orderService;
    private PizzaService pizzaService;
    private final Logger logger;

    public OrderController(OrderService orderService, PizzaService pizzaService, Logger logger) {
        this.orderService = orderService;
        this.pizzaService = pizzaService;
        this.logger = logger;
    }

    @GetMapping("/pizzas")
    public Map<String, Long> getOrders() {
        logger.info("\u001B[33mGET{pizzas} method CALLED from OrderController\u001B[0m");
        Map<String, Long> orders = new HashMap<>();
        pizzaService.findAll().forEach(pizza -> orders.put(pizza.getName(), (long) orderService.findByPizza(pizza).size()));
        return orders;
    }

    @GetMapping("/hours")
    public Map<Integer, Long> getFavoriteHours() {
        logger.info("\u001B[33mGET{hours} method CALLED from OrderController\u001B[0m");
        Map<Integer, Long> favoriteHours = new HashMap<>();
        for (int i = 1; i <= 24; i++) {
            AtomicLong orders = new AtomicLong(0L);
            int finalI = i;
            orderService.findAll().forEach(pizzaOrder -> {
                if (finalI == pizzaOrder.getDateCreated().getHour()) {
                    orders.getAndIncrement();
                }
            });
            favoriteHours.put(i, orders.get());
        }
        return favoriteHours;
    }
}