package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */

@SuppressWarnings("OptionalGetWithoutIsPresent")
@WebServlet(urlPatterns = "/pizzaSize")
@AllArgsConstructor
public class PizzaSizeServlet extends HttpServlet {
    private final PizzaService pizzaService;
    private final SpringTemplateEngine springTemplateEngine;
    private final UserService userService;
    private final Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\u001B[33mGET method CALLED from PizzaSize Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Pizza selectedPizza;
        if (req.getParameter("selectedPizza").isEmpty())
            selectedPizza = (Pizza) req.getSession().getAttribute("selectedPizza");
        else
            selectedPizza = pizzaService.findByName(req.getParameter("selectedPizza"));
        req.getSession().setAttribute("selectedPizza", selectedPizza);
        context.setVariable("selectedPizza", selectedPizza);
        context.setVariable("user", (User) req.getSession().getAttribute("user"));
        this.springTemplateEngine.process("selectPizzaSize.html", context, resp.getWriter());
    }
}
