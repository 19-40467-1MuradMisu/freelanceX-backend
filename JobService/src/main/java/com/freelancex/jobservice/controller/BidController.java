package com.freelancex.jobservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bid")
public class BidController {

    @GetMapping()
    public String bid() {
        return "Bidding Service";
    }
}