package com.application.management.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AjaxSessionTimeoutFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String ajaxHeader = httpRequest.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        
        if (isAjax) {
            HttpSession session = httpRequest.getSession(false);
            
            // Check if user is actually logged in via Spring Security's session attribute
            Object principal = session != null ? session.getAttribute("SPRING_SECURITY_CONTEXT") : null;
            boolean sessionValid = principal != null;
            
            if (!sessionValid) {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpResponse.setContentType("application/json;charset=UTF-8");
                httpResponse.getWriter().write("{\"error\":\"Session expired, please login again\"}");
                httpResponse.getWriter().flush();
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
}
