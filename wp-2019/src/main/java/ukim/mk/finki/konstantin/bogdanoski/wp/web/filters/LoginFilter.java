package ukim.mk.finki.konstantin.bogdanoski.wp.web.filters;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        String username = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");

        if (path.equals("/register"))
            filterChain.doFilter(servletRequest, servletResponse);

        boolean flag = false;
        if (!(username == null || username.isEmpty()))
            flag = userService.findAll().contains(new User(username, password));
        if (!"/login".equals(path) && !flag)
            response.sendRedirect("/login");
        else
            filterChain.doFilter(servletRequest, servletResponse);
    }
}
