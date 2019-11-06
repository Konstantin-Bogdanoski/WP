package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.OrderService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@SuppressWarnings("ALL")
@WebServlet(urlPatterns = "/PizzaOrder.do")
@AllArgsConstructor
public class PizzaOrderServlet extends HttpServlet {
    private final UserService userService;
    private final OrderService orderService;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\u001B[33mGET method CALLED from PizzaOrder Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();

        Pizza pizza = (Pizza) session.getAttribute("selectedPizza");
        context.setVariable("selectedPizza", pizza);

        String size = req.getParameter("size");
        context.setVariable("size", size);
        session.setAttribute("size", size);

        User user = userService.findByUsername((String) session.getAttribute("username")).get();

        context.setVariable("user", user);

        this.springTemplateEngine.process("deliveryInfo.html", context, resp.getWriter());
    }
}