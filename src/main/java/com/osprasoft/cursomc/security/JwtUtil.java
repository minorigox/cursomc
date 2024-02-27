package com.osprasoft.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setExpiration(
                new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.ES512, secret.getBytes()).compact();
    }

}
