package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.OrderService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/ConfirmationInfo.do")
@AllArgsConstructor
public class ConfirmationInfo extends HttpServlet {
    private PizzaService pizzaService;
    private UserService userService;
    private OrderService orderService;
    private SpringTemplateEngine springTemplateEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = userService.findOne((Long) session.getAttribute("username")).get();
        Pizza pizza = pizzaService.findOne((Long) session.getAttribute("pizza")).get();
        PizzaOrder order = orderService.findOne((Long) session.getAttribute("order")).get();
        String size = order.getSize();
        order.setDateCreated(LocalDateTime.now());
        context.setVariable("username", user.getId());
        context.setVariable("pizza", pizza.getId());
        context.setVariable("order", order.getId());
        context.setVariable("size", size);
        this.springTemplateEngine.process("confirmationInfo.html", context, resp.getWriter());
    }
}
