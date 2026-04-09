package com.auth.user.filters;

import com.auth.user.util.HeaderConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {


    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/v1/user/login",
            "/api/v1/user/sign-up",
            "/api/v1/user/reset-password"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // TODO :: remove this just meant for development
        if(true){
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();

        if (isExcluded(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("[JwtFilter] : Incoming request: " + request.getMethod() + " " + path);

        String token = request.getHeader(HeaderConstants.HEADER_ACCESS_TOKEN);
        if (Objects.isNull(token) || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access token is missing.");
            return;
        }

        if(!validateToken(token)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isExcluded(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }


    private boolean validateToken(String token){
        try {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();

            if (expiration.before(new Date())) {
                System.out.println("[JwtFilter] Token expired");
                return false;
            }

            return true;

        } catch (Exception e) {
            System.out.println("[JwtFilter] Token validation failed: " + e.getMessage());
            return false;
        }
    }
}