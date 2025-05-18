package com.freelancex.skillservice.services.interfaces;

import com.freelancex.skillservice.model.Certification;

import java.util.UUID;

public interface VerificationService {
    Certification verifySkill(UUID userId, String skills);
}
