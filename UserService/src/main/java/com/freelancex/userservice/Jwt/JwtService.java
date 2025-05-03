package com.freelancex.userservice.Jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;


import org.springframework.stereotype.Service;

import com.freelancex.userservice.model.User;
import com.freelancex.userservice.model.User.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "letsstarthehhe";

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new java.util.Date());
    }

    public String generateToken(User userDetails) {
          Role role = userDetails.getRole();


    if (role == null || role.equals(Role.ADMIN) || role.equals(Role.FREELANCER)) {
        role = Role.CLIENT; 
    }

        return Jwts.builder()
                .setSubject(userDetails.getEmail())
                .claim("roles", userDetails.getRole())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) 
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}


