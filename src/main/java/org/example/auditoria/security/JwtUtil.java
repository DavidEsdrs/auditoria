package org.example.auditoria.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.expiration}")
    private int JWT_EXPIRATION_MS;

    private SecretKey key;

    @PostConstruct
    public void init() {
        System.out.println(JWT_SECRET_KEY);
        System.out.println(JWT_EXPIRATION_MS);
        key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key).build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}