package com.osprasoft.cursomc.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.osprasoft.cursomc.domain.UserSS;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateTokenAlt(String username) {
        return Jwts.builder().setSubject(username).setExpiration(
                new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.ES512, secret.getBytes()).compact();
    }

    public String generateToken(UserSS user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create().withIssuer("auth-api")
            .withSubject(user.getUsername())
            .withExpiresAt(genExpirationDate()).sign(algorithm);
        return token;
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).withIssuer("auth-api")
            .build().verify(token).getSubject();
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2)
            .toInstant(ZoneOffset.of("-03:00"));
    }

}
