package com.prueba.proteccion.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.proteccion.exceptions.ErrorMessage;
import com.prueba.proteccion.services.JwtServiceImpl;
import com.prueba.proteccion.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;

    private final UserDetailsServiceImpl userDetailsService;

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtServiceImpl jwtService, UserDetailsServiceImpl userDetailsService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        try {
            jwtService.validateToken(token);
            String username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                handleUnauthorizedAccess(response, "Nombre de usuario no encontrado");
                return;
            }
        } catch (Exception e) {
            handleUnauthorizedAccess(response, "Acceso no autorizado");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void handleUnauthorizedAccess(HttpServletResponse response, String message) throws IOException {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, message);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorMessage));
    }
}
