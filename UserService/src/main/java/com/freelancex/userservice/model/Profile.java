package com.freelancex.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
public class Profile {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "profile_id", updatable = false, nullable = false)
    private UUID profileId;

    @Setter
    @Column(nullable = false, length = 50)
    private String firstName;

    @Setter
    @Column(nullable = false, length = 50)
    private String lastName;

    @Setter
    @Column(nullable = true, length = 1000)
    private String bio;

    @Setter
    @Column(nullable = false)
    private boolean isSkillVerified = false;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
