package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
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
@WebServlet(urlPatterns = "/register")
@AllArgsConstructor
public class RegisterServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        springTemplateEngine.process("register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        User user = new User();
        user.setFirstName((String) context.getSession().getAttribute("fname"));
        user.setLastName((String) context.getSession().getAttribute("lname"));
        user.setPassword((String) context.getSession().getAttribute("password"));
        user.setUsername((String) context.getSession().getAttribute("username"));
        user.setUserRole("ROLE_USER");
        userService.save(user);
        context.getSession().setAttribute("user", user);
        resp.sendRedirect("/");
    }
}
