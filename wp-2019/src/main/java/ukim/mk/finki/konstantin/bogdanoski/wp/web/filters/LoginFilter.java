package ukim.mk.finki.konstantin.bogdanoski.wp.web.filters;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebFilter
public class LoginFilter implements Filter {

    private UserService userService;

    public LoginFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        String username = request.getSession().getAttribute("username").toString();
        String password = request.getSession().getAttribute("password").toString();

        if (!"/login".equals(path) && (username == null || username.isEmpty() || password == null || password.isEmpty()) && !userService.findAll().contains(new User(username)))
            response.sendRedirect("/login");

        if (userService.findByUsername(username).getPassword().equals(password))
            filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
