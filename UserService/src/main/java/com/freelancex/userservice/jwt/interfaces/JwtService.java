package com.freelancex.userservice.jwt.interfaces;

import java.util.UUID;

public interface JwtService {
    String generateToken(UUID userId, String role);
}
