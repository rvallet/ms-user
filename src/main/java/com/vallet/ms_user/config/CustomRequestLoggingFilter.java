package com.vallet.ms_user.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomRequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        // Filtrage des requêtes Swagger UI ou doc
        boolean isSwaggerRequest = requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs");
        filterChain.doFilter(request, response);

        // Loguer l'état de la réponse seulement si ce n'est pas une requête Swagger
        if (!isSwaggerRequest) {
            LOGGER.info("{} \"{}\" - Response: {}", method, requestURI, response.getStatus());
        }

    }

}
