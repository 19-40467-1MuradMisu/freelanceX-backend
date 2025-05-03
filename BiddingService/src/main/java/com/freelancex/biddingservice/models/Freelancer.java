package com.freelancex.biddingservice.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("FREELANCER")
@Getter
public class Freelancer extends User {
}
