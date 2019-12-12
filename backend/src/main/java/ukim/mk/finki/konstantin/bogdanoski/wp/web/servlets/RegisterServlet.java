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
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/register")
@AllArgsConstructor
public class RegisterServlet extends HttpServlet {
    private final UserService userService;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\u001B[33mGET method CALLED from Register Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        springTemplateEngine.process("register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\u001B[33mPOST method CALLED from Register Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("newusername");
        String password = req.getParameter("newpassword");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");

        User user = new User(username, password);
        user.setUserRole("ROLE_USER");
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setDateCreated(LocalDateTime.now());
        req.getSession().setAttribute("username", username);
        req.getSession().setAttribute("password", password);
        if (!userService.findByUsername(username).isPresent())
            userService.save(user);
        resp.sendRedirect("/login");
    }
}
