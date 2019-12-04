package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.WebContext;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.*;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.*;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private PizzaService pizzaService;
    private IngredientService ingredientService;
    private PizzaIngredientService pizzaIngredientService;
    private OrderService orderService;
    private Logger logger;

    public AdminController(UserService userService, PizzaService pizzaService, IngredientService ingredientService, PizzaIngredientService pizzaIngredientService, OrderService orderService, Logger logger) {
        this.userService = userService;
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
        this.pizzaIngredientService = pizzaIngredientService;
        this.orderService = orderService;
        this.logger = logger;
    }

    @GetMapping
    public ModelAndView getAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for ADMIN PAGE CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("orders", orderService.findAll());
            modelAndView.setViewName("master-admin");
            modelAndView.addObject("bodyContent", "body-orders");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/ingredients")
    public ModelAndView getIngredients(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET INGREDIENTS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            modelAndView.addObject("ingredients", ingredientService.findAll());
            modelAndView.addObject("bodyContent", "body-ingredients");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/addIngredient")
    public ModelAndView addIngredient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for ADD INGREDIENT CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            modelAndView.addObject("ingredient", new Ingredient());
            modelAndView.addObject("bodyContent", "body-add-ingredient");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/editIngredient/{id}")
    public ModelAndView editIngredient(HttpServletRequest req, HttpServletResponse resp, @PathVariable(name = "id") Long ingredientID) throws IOException {
        logger.info("\u001B[33mGET method for EDIT INGREDIENT CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();

        Ingredient oldIngredient = null;
        if (ingredientService.findOne(ingredientID).isPresent())
            oldIngredient = ingredientService.findOne(ingredientID).get();
        else
            throw new IngredientNotFoundException();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            modelAndView.addObject("ingredient", oldIngredient);
            modelAndView.addObject("bodyContent", "body-edit-ingredient");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/pizzas")
    public ModelAndView getPizzas(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET PIZZAS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            modelAndView.addObject("pizzas", pizzaService.findAll().stream().sorted().collect(Collectors.toList()));
            modelAndView.addObject("bodyContent", "body-pizzas");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/users")
    public ModelAndView getUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET USERS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            modelAndView.addObject("users", userService.findAll().stream().sorted().collect(Collectors.toList()));
            modelAndView.addObject("bodyContent", "body-users");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/editPizza/{id}")
    public ModelAndView editPizza(@PathVariable(name = "id") Long pizzaID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for EDIT PIZZA CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            Pizza oldPizza;
            if (pizzaService.findOne(pizzaID).isPresent())
                oldPizza = pizzaService.findOne(pizzaID).get();
            else
                throw new PizzaNotFoundException();
            modelAndView.addObject("pizza", oldPizza);
            modelAndView.addObject("ingredientList", ingredientService.findAll());
            modelAndView.addObject("newIngredients", new ArrayList<Long>());
            modelAndView.addObject("bodyContent", "body-edit-pizza");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/editUser/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") Long userID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for EDIT USER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        User oldUser;
        if (userService.findOne(userID).isPresent())
            oldUser = userService.findOne(userID).get();
        else
            throw new UserNotFoundException();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            modelAndView.addObject("user", oldUser);
            modelAndView.addObject("bodyContent", "body-edit-user");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/addPizza")
    public ModelAndView addPizza(@ModelAttribute Pizza pizza, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for ADD PIZZA CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            modelAndView.addObject("pizza", new Pizza());
            modelAndView.addObject("ingredientList", ingredientService.findAll());
            modelAndView.addObject("newIngredients", new ArrayList<Long>());
            modelAndView.addObject("bodyContent", "body-add-pizza");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }

    @DeleteMapping("/orders/{userid}/{orderid}")
    public ModelAndView deleteOrderOfUser(@PathVariable(name = "orderid") Long orderID, @PathVariable(name = "userid") Long userID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for DELETE ORDER BY USER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            if (!orderService.findOne(orderID).isPresent())
                throw new OrderNotExistsException();
            orderService.delete(orderID);
            return new ModelAndView("redirect:/admin/orders/" + userID);
        } else
            throw new UnauthorizedAccessException();
    }

    @DeleteMapping("/orders/{orderid}")
    public ModelAndView deleteOrder(@PathVariable(name = "orderid") Long orderID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for DELETE ORDER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            if (!orderService.findOne(orderID).isPresent())
                throw new OrderNotExistsException();
            orderService.delete(orderID);
            return new ModelAndView("redirect:/admin");
        } else
            throw new UnauthorizedAccessException();
    }

    @GetMapping("/orders/{id}")
    public ModelAndView getOrdersFromUser(@PathVariable(name = "id") Long userID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET ORDERS BY USER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();
        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();
        if (!userService.findOne(userID).isPresent())
            throw new UserNotFoundException();
        User specUser = userService.findOne(userID).get();
        if (user.getUserRole().equals("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("master-admin");
            List<PizzaOrder> ordersFromUser = orderService.findAll().stream().filter(order -> order.getUser().equals(specUser)).collect(Collectors.toList());
            modelAndView.addObject("orders", ordersFromUser);
            modelAndView.addObject("user", userService.findOne(userID).get());
            modelAndView.addObject("bodyContent", "body-user-orders");
            return modelAndView;
        } else
            throw new UnauthorizedAccessException();
    }
}
