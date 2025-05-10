package com.freelancex.apigateway.jwt;

import com.freelancex.apigateway.dtos.JwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public JwtPayload validateToken(String token) {

        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token)
                .getPayload();

        String role = (String) claims.get("role");
        String userId = (String) claims.get("userId");

        if (role == null || userId == null) {
            throw new JwtException("Invalid token");
        }

        return new JwtPayload(userId, role);
    }
}
