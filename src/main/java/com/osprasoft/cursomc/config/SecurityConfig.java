package com.osprasoft.cursomc.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private Environment env;

    private static final String[] PUBLIC_MATCHERS = {
        "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
        "/produtos/**",
        "/categorias/**",
        "/clientes/*"
    };

     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {    
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers((headers) -> headers.disable());
        }

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
            auth -> auth.requestMatchers(
                HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()).sessionManagement(
                    sm -> sm.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
