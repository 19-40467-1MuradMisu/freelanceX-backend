package com.freelancex.biddingservice.dtos;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateEvent {
    private UUID userId;

    private String firstName;

    private String lastName;
}
