package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
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

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/admin")
@AllArgsConstructor
public class AdminServlet extends HttpServlet {
    private UserService userService;
    private OrderService orderService;
    private SpringTemplateEngine springTemplateEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        User user = new User();

        if (userService.findByUsername((String) session.getAttribute("username")).isPresent())
            user = userService.findByUsername((String) session.getAttribute("username")).get();

        if (user.getUserRole().equals("ROLE_ADMIN")) {
            context.setVariable("orders", orderService.findAll());
            session.setAttribute("orders", orderService.findAll());
            springTemplateEngine.process("admin.html", context, resp.getWriter());
        } else
            resp.sendError(404, "YOU ARE NOT ALLOWED TO BE HERE!");
    }
}
