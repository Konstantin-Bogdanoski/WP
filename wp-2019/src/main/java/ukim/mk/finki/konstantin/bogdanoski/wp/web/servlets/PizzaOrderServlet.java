package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
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

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/PizzaOrder.do")
@AllArgsConstructor
public class PizzaOrderServlet extends HttpServlet {
    private UserService userService;
    private OrderService orderService;
    private SpringTemplateEngine springTemplateEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        PizzaOrder order = new PizzaOrder();
        Pizza pizza = (Pizza) session.getAttribute("selectedPizza");
        req.getSession().setAttribute("selectedPizza", pizza);
        context.setVariable("selectedPizza", pizza);
        String size = req.getParameter("size");
        context.setVariable("size", size);
        order.setSize(size);
        order.setPizza(pizza);
        User user = userService.findByUsername((String) session.getAttribute("username")).get();
        order.setUser(user);
        context.setVariable("user", user);
        session.setAttribute("user", user);
        orderService.save(order);
        context.setVariable("order", order.getId());
        session.setAttribute("order", order.getId());
        this.springTemplateEngine.process("deliveryInfo.html", context, resp.getWriter());
    }
}