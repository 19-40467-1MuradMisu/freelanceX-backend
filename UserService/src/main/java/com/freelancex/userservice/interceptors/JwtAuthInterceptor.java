package com.freelancex.userservice.interceptors;

import com.freelancex.userservice.dtos.common.JwtBody;
import com.freelancex.userservice.exception.JwtException;
import com.freelancex.userservice.jwt.interfaces.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public JwtAuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid Authorization header");
            return false;
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        try {
            JwtBody jwtBody = jwtService.validateToken(token);
            request.setAttribute("email", jwtBody.email());
            request.setAttribute("role", jwtBody.role());
            return true;
        } catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid token: " + e.getMessage());
            return false;
        }
    }
}
