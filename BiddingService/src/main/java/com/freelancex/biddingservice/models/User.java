package com.freelancex.biddingservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Setter
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Setter
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
}

