package com.freelancex.biddingservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class UserCreateEventDto {
    @Getter
    @Setter
    private UUID userId;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;
}
