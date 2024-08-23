package com.radion.taskmanagementsystems.config.jwt;

import com.radion.taskmanagementsystems.exception.BadAuthenticationException;
import com.radion.taskmanagementsystems.exception.UserNotFoundException;
import com.radion.taskmanagementsystems.service.UserService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(HEADER_NAME);

        if (authHeader == null || authHeader.isBlank()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"forbidden\": \"Authorization header is missing or empty\"}");
            response.getWriter().flush();
            return; // Не продолжаем фильтрацию
        }

        if (!authHeader.startsWith(BEARER_PREFIX)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"forbidden\": \"Authorization header does not start with Bearer prefix\"}");
            response.getWriter().flush();
            return; // Не продолжаем фильтрацию
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String email;
        try {
            email = jwtService.extractEmail(jwt);
        }catch (BadAuthenticationException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"forbidden\": \"Invalid token\"}");
            response.getWriter().flush();
            return;
        }

        try {
            if (!email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(email);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (BadAuthenticationException | UserNotFoundException ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"forbidden\": \"" + ex.getMessage() + "\"}");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }
}