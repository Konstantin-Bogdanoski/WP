package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/PizzaOrder.do")
@AllArgsConstructor
public class PizzaSizeServlet extends HttpServlet {
    private PizzaService pizzaService;
    private UserService userService;
    private SpringTemplateEngine springTemplateEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        PizzaOrder order = new PizzaOrder();
        User user = userService.findByUsername((String) context.getSession().getAttribute("username"));
        Pizza selectedPizza = pizzaService.findByName((String) context.getSession().getAttribute("pizza"));
        order.setUser(user);
        order.setPizza(selectedPizza);
        context.setVariable("username", user.getId());
        context.setVariable("selectedPizza", selectedPizza.getId());
        context.setVariable("order", order.getId());
        this.springTemplateEngine.process("selectPizzaSize.html", context, resp.getWriter());
    }
}