package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
@WebServlet(name = "index", urlPatterns = "")
@AllArgsConstructor
public class ShowPizzaServlet extends HttpServlet {
    private final PizzaService pizzaService;
    private final UserService userService;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\u001B[33mGET method CALLED from ShowPizza Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Pizza> pizzas = pizzaService.findAll();
        context.setVariable("username", userService.findByUsername((String) session.getAttribute("username")).get().getUsername());
        context.setVariable("pizzas", pizzas);
        context.setVariable("user", userService.findByUsername((String) session.getAttribute("username")).get());
        session.setAttribute("user", userService.findByUsername((String) session.getAttribute("username")).get());
        this.springTemplateEngine.process("listPizzas.html", context, resp.getWriter());
    }
}
