package com.freelancex.biddingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BiddingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiddingServiceApplication.class, args);
    }

}
