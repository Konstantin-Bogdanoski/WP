package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.WebContext;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.UnauthorizedAccessException;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

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
}
