package ukim.mk.finki.konstantin.bogdanoski.wp.web.servlets;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/login")
@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        this.springTemplateEngine.process("login.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute("username", req.getParameter("username"));
        session.setAttribute("password", req.getParameter("password"));
        resp.sendRedirect("/pizzas");
    }
}
