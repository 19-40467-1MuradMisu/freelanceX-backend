package com.freelancex.biddingservice.models;

import com.freelancex.biddingservice.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @Setter
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Setter
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Setter
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Setter
    @Column(nullable = false, length = 50)
    private UserRole role;
}

