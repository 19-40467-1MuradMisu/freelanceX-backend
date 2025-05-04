package com.freelancex.userservice.dtos.event;

import java.util.UUID;

public record SkillVerifiedEvent(UUID userId, boolean verified) {
}
