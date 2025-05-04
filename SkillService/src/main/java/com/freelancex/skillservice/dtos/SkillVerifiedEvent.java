package com.freelancex.skillservice.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class SkillVerifiedEvent {
    
    private UUID userId;

    private boolean verified;
}
