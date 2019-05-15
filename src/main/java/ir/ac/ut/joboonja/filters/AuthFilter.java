package ir.ac.ut.joboonja.filters;

import ir.ac.ut.joboonja.auth.JWTUtils;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.services.UserService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!request.getRequestURI().contains("auth")) {
            String token = request.getHeader("Authorization");
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain");
                PrintWriter writer = response.getWriter();
                writer.print("Unauthorized!");
            }
            else {
                String username = JWTUtils.verifyJWT(token);
                if (username == null) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("text/plain");
                    PrintWriter writer = response.getWriter();
                    writer.print("Access denied!");
                } else {
                    User user = UserService.getUserByUserName(username);
                    request.setAttribute("user", user);
                    chain.doFilter(request, response);
                }
            }

        }
        else
            chain.doFilter(request, servletResponse);
    }
}