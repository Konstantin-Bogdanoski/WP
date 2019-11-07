package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.OrderService;
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
@WebServlet(urlPatterns = "/delete")
public class DeleteOrdersServlet extends HttpServlet {
    private final UserService userService;
    private final OrderService orderService;

    public DeleteOrdersServlet(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        if (userService.findByUsername((String) req.getSession().getAttribute("username")).isPresent()) {
            user = userService.findByUsername((String) req.getSession().getAttribute("username")).get();
            User finalUser = user;
            orderService.findAll().forEach(order -> {
                if (order.getUser().equals(finalUser))
                    orderService.delete(order);
            });
        }
        resp.sendRedirect("/");
    }
}
