package ukim.mk.finki.konstantin.bogdanoski.wp.web.filters;

import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
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

        if (path.equals("/register") && (username == null || username.isEmpty()) && (password == null || password.isEmpty()))
            filterChain.doFilter(servletRequest, servletResponse);

        else {
            boolean flag = false;
            if (!(username == null || username.isEmpty()) && !(password == null || password.isEmpty()))
                flag = userService.findByUsername(username).get().getPassword().equals(password);
            if (!"/login".equals(path) && !flag)
                response.sendRedirect("/login");
            else
                filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
