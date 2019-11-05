package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.OrderService;

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
    private OrderService orderService;
    private SpringTemplateEngine springTemplateEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        Long id = (Long) session.getAttribute("order");
        String address = req.getParameter("address");
        session.setAttribute("address", address);
        context.setVariable("address", address);
        PizzaOrder order = orderService.findOne(id).get();
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
