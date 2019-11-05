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

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/register")
@AllArgsConstructor
public class RegisterServlet extends HttpServlet {
    private final UserService userService;
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        springTemplateEngine.process("register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("newusername");
        String password = req.getParameter("newpassword");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String pathToRedirect = "";

        if (username == null
                || username.equals("")
                || password == null
                || password.equals("")
                || fname == null
                || fname.equals("")
                || lname == null
                || lname.equals(""))
            pathToRedirect = "/register";
        else {
            User user = new User(username, password);
            user.setUserRole("ROLE_USER");
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setDateCreated(LocalDateTime.now());
            req.getSession().setAttribute("username", username);
            req.getSession().setAttribute("password", password);
            if (!userService.findByUsername(username).isPresent())
                userService.save(user);
            pathToRedirect = "/";
        }
        resp.sendRedirect(pathToRedirect);
    }
}
