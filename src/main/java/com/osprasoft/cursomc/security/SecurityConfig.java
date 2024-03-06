package com.osprasoft.cursomc.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private Environment env;

    @Autowired
    private SecurityFilter securityFilter;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/produtos/**",
            "/categorias/**",
            "/estados/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/clientes/**",
            "/clientes/picture",
            "/forgot/**",
            "/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers((headers) -> headers.disable());
        }

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Prof. Nelio Alves
    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    // final UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", new
    // CorsConfiguration().applyPermitDefaultValues());
    // return source;
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
