package com.freelancex.biddingservice.dtos.common;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class Response {

    private String message;

    private int statusCode;
}
