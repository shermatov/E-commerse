package com.shermatov.dreamshops.security.config;

import com.shermatov.dreamshops.security.jwt.AuthTokenFilter;
import com.shermatov.dreamshops.security.jwt.JwtAuthEntryPoint;
import com.shermatov.dreamshops.security.jwt.JwtUtils;
import com.shermatov.dreamshops.security.user.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class ShopConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ShopConfig.class);
    private final ShopUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint authEntryPoint;

    private final JwtUtils jwtUtils;

    // this will lock the api
    private static final List<String> SECURED_URLS = List.of("/api/v1/cart/**","/api/v1/cartItems/**");

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
//    public AuthTokenFilter authTokenFilter() {
//        return new AuthTokenFilter();
//    }
    @Bean
    AuthTokenFilter authTokenFilter() {
        logger.debug("Creating AuthTokenFilter bean");
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        logger.debug("Creating DaoAuthenticationProvider bean");
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.debug("Configuring Security Filter Chain");
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception-> exception.authenticationEntryPoint (authEntryPoint))
                .sessionManagement (session-> session.sessionCreationPolicy(SessionCreationPolicy
                        .STATELESS))
                .authorizeHttpRequests(auth-> auth.requestMatchers (SECURED_URLS.toArray(String[]::new))
                        .authenticated()
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui.html",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**")
                        .permitAll());
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


}