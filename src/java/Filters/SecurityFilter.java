package Filters;

import Models.Role;
import Models.User;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SecurityFilter implements Filter {

    private FilterConfig filterConfig = null;

    static {
        SecurityConfig.GetInstance().AddProtectLink("/app/reset-password", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/home", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/login", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/register", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/subjectlist", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/changepassword", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/userprofile", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/bloglist", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/testRole", Role.ROLE_CUSTOMER);
        SecurityConfig.GetInstance().AddProtectLink("/app/blogdetail", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/subject-register", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/subject-details", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/requestlogin-google", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/login-google", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/requestFacebook", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/login-facebook", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/dashboard", Role.ROLE_ADMIN);
        SecurityConfig.GetInstance().AddProtectLink("/app/dashboard", Role.ROLE_MARKETING);
        SecurityConfig.GetInstance().AddProtectLink("/app/dashboard", Role.ROLE_SALE);
        SecurityConfig.GetInstance().AddProtectLink("/app/practicelist", Role.ROLE_CUSTOMER);
        SecurityConfig.GetInstance().AddProtectLink("/app/slider-list", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/slider-details", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/practicelist", Role.ROLE_CUSTOMER);
        SecurityConfig.GetInstance().AddProtectLink("/app/quiz-review", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/quiz-handle", Role.ROLE_CUSTOMER);
        SecurityConfig.GetInstance().AddProtectLink("/app/simulation-exam", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/registration-list", Role.ROLE_SALE);
        SecurityConfig.GetInstance().AddProtectLink("/app/registration-details", Role.ROLE_SALE);
        SecurityConfig.GetInstance().AddProtectLink("/app/practicereview", Role.ROLE_GUEST);
        SecurityConfig.GetInstance().AddProtectLink("/app/newpractice", Role.ROLE_GUEST);
    }

    public SecurityFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String currentUrl = httpRequest.getRequestURI();
        if (!currentUrl.contains("Assets")) {
            if (SecurityConfig.GetInstance().IsValid(currentUrl, currentUser)) {
                chain.doFilter(request, response);
            } else {
                if (SecurityConfig.GetInstance().IsNotFound(currentUrl)) {
                    httpResponse.sendError(404, "Page Not Found");
                } else {
                    httpResponse.sendRedirect("home?action=login&message=Please login. You don't have permission to access!");
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
        }
    }
}
