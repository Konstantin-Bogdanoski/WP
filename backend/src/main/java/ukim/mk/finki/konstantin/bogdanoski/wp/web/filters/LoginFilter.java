package ukim.mk.finki.konstantin.bogdanoski.wp.web.filters;

import lombok.AllArgsConstructor;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebFilter
@AllArgsConstructor
public class LoginFilter implements Filter {

    private final UserService userService;
    private final Logger logger;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("\u001B[35mDoFILTER method CALLED from Login Filter\u001B[0m");
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
                if (userService.findByUsername(username).isPresent())
                    flag = userService.findByUsername(username).get().getPassword().equals(password);
            if (!"/login".equals(path) && !flag)
                response.sendRedirect("/login");
            else
                filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
