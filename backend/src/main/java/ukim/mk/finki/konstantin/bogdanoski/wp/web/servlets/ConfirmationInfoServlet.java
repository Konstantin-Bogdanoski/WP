package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import eu.bitwalker.useragentutils.UserAgent;
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
@WebServlet(urlPatterns = "/ConfirmationInfo.do")
@AllArgsConstructor
public class ConfirmationInfoServlet extends HttpServlet {
    private final OrderService orderService;
    private final UserService userService;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\u001B[33mGET method CALLED from ConfirmationInfo Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        String address = req.getParameter("address");
        session.setAttribute("address", address);
        context.setVariable("address", address);
        PizzaOrder order = new PizzaOrder();

        order.setAddress(address);
        order.setUser((User) session.getAttribute("user"));
        order.setPizza((Pizza) session.getAttribute("selectedPizza"));
        order.setSize((String) session.getAttribute("size"));
        order.setDateCreated(LocalDateTime.now());
        orderService.save(order);

        UserAgent userAgent = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));

        User user = (User) session.getAttribute("user");
        Pizza selectedPizza = (Pizza) session.getAttribute("selectedPizza");
        req.setAttribute("user", user);
        req.setAttribute("selectedPizza", selectedPizza);
        req.setAttribute("address", address);
        req.setAttribute("order", order);
        req.setAttribute("size", session.getAttribute("size"));
        req.setAttribute("browser", userAgent.getBrowser().getName());
        req.setAttribute("ip", req.getRemoteAddr());

        this.springTemplateEngine.process("confirmationInfo.html", context, resp.getWriter());
    }
}
