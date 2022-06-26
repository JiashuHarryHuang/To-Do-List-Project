package com.to_do_list.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Log in filter: check whether user is logged in when accessing resources
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {

    //Used to compare paths
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * When user is accessing resource, check whether he/she has already logged in
     * @param servletRequest Servlet request
     * @param servletResponse Servlet response
     * @param filterChain Filter chain
     * @throws IOException IOException
     * @throws ServletException Servlet Exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //Get current URI
        String requestURI = request.getRequestURI();
        log.info("Accessing URI: {}", requestURI);

        //URIs that don't need to be handled
        String[] uris = {
                "/user/signup",
                "/user/login",
                "/page/**",
                "/plugins/**",
                "/styles/**",
                "/img/**"
        };

        //Check whether the request needs to be handled
        boolean check = checkURI(uris, requestURI);

        if (check) { //Request doesn't need to be handled
            log.info("{} doesn't need to be handled", requestURI);
            filterChain.doFilter(request, response);
        } else {
            Long userId = (Long) request.getSession().getAttribute("user");

            if (userId != null) { //User has logged in
                log.info("User has logged in");
                filterChain.doFilter(request, response);
            } else {
                //User has not logged in, so redirect to login.html
                log.info("User has not logged in. Redirecting to log in page");
                response.sendRedirect("/page/login/login.html");
            }
        }
    }

    /**
     * Check whether requestURI is in uris
     * @param uris Paths that don't need to be handled
     * @param requestURI Current request URI
     * @return Whether requestURI is in uris
     */
    private boolean checkURI(String[] uris, String requestURI) {
        for (String uri : uris) {
            if (PATH_MATCHER.match(uri, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
