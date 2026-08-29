package com.lulak.frugo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class JwtSecurity {

    private final String SECRET = "12345678901234567890123456789012";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username, List<String> roles){
        long expiration = 100 * 60 * 60 * 3;

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public List<String> extractRoles(String token){
        return extractClaims(token).get("roles", List.class);
    }

    public boolean isTokenVailid(String token){
        try{
            extractClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
