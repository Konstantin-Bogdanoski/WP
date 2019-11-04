package ukim.mk.finki.konstantin.bogdanoski.wp.web.filters;

import lombok.AllArgsConstructor;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebFilter(urlPatterns = "/register")
@AllArgsConstructor
public class RegisterFilter implements Filter {

    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        String username = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");
        String fname = (String) request.getSession().getAttribute("fname");
        String lname = (String) request.getSession().getAttribute("lname");

        //TODO: Implement Register

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
