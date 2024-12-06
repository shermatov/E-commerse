package com.shermatov.dreamshops.security.jwt;

import com.shermatov.dreamshops.security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    private final JwtUtils jwtUtils;
    private final ShopUserDetailsService shopUserDetailsService;


    public AuthTokenFilter(JwtUtils jwtUtils, ShopUserDetailsService shopUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.shopUserDetailsService = shopUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull  HttpServletResponse response,
                                    @NonNull  FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if(StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)){// if jwt is not empty and is valid token
                String username = jwtUtils.getUsernameFromToken(jwt);// get the username from token
                logger.debug("JWT token is valid. Extracted username: {}", username);
                UserDetails userDetails = shopUserDetailsService.loadUserByUsername(username);
                var auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.debug("User authenticated and security context updated for user: {}", username);
            }
        } catch (JwtException e) {
            logger.error("Invalid or expired JWT token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage() + ": Invalid or expired token, you may try again later!");
            return;
        }catch (Exception e){
            logger.error("Unexpected error during JWT authentication: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;

        }
        filterChain.doFilter(request,response);

    }
    // this method capture the request and extract token from header
    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");// capture request from client and set in headerAuth starting with request bearer
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }
}