package org.myApp.uibackend.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Logs inbound HTTP requests with secret-safe masking.
 *
 * - CI + Selenium failures need request context
 * - Secrets must NEVER leak into logs
 * - Logging happens at system boundary, once
 */
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // extract basic request info
        String method = request.getMethod();
        String path = request.getRequestURI();

        // Masked query string (passwords removed)
        String query = MaskingUtils.mask(request.getQueryString());

        System.out.printf(
                "[REQ] %s %s%s%n",
                method,
                path,
                query != null ? "?" + query : ""
        );

        // Continue request chain
        filterChain.doFilter(request, response);

    }
}
