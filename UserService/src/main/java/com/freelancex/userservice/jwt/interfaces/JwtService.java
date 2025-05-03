package com.freelancex.userservice.jwt.interfaces;

import com.freelancex.userservice.dtos.common.JwtBody;
import com.freelancex.userservice.exception.JwtException;

public interface JwtService {
    String generateToken(String email, String role);

    JwtBody validateToken(String token) throws JwtException;
}
