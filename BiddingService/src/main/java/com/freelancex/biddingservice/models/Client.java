package com.freelancex.biddingservice.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("CLIENT")
@Getter
public class Client extends User {
}
