package com.radion.taskmanagementsystems.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radion.taskmanagementsystems.exception.BadAuthenticationException;
import com.radion.taskmanagementsystems.exception.UserNotFoundException;
import com.radion.taskmanagementsystems.exception.model.ExceptionModel;
import com.radion.taskmanagementsystems.service.UserService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;
    private final MessageSource messageSource;
    private static final List<AntPathRequestMatcher> OPEN_PATH_MATCHERS = List.of(
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/api/auth/sign-in"),
            new AntPathRequestMatcher("/api/auth/sign-up")
    );
    private ObjectMapper objectMapper = new ObjectMapper();

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
            response.getWriter().write(Objects.requireNonNull(
                    toJson(ExceptionModel.builder()
                            .status(HttpStatus.FORBIDDEN.toString())
                            .content(messageSource.getMessage("headerException", null, Locale.getDefault()))
                            .build()))
            );
            response.getWriter().flush();
            return;
        }

        if (!authHeader.startsWith(BEARER_PREFIX)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write(Objects.requireNonNull(
                    toJson(ExceptionModel.builder()
                            .status(HttpStatus.FORBIDDEN.toString())
                            .content(messageSource.getMessage("bearer", null, Locale.getDefault()))
                            .build()))
            );
            response.getWriter().flush();
            return;
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String email;
        try {
            email = jwtService.extractEmail(jwt);
        }catch (BadAuthenticationException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write(Objects.requireNonNull(
                    toJson(ExceptionModel.builder()
                            .status(HttpStatus.FORBIDDEN.toString())
                            .content("Invalid token")
                            .build()))
            );
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
            response.getWriter().write(Objects.requireNonNull(
                    toJson(ExceptionModel.builder()
                            .status(HttpStatus.FORBIDDEN.toString())
                            .content(ex.getMessage())
                            .build()))
            );
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return OPEN_PATH_MATCHERS.stream().anyMatch(matcher -> matcher.matches(request));
    }

    private String toJson(ExceptionModel exceptionModel){
        try {
            return objectMapper.writeValueAsString(exceptionModel);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}