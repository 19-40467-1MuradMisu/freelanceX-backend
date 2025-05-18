package com.freelancex.biddingservice.dtos.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.freelancex.biddingservice.views.Views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView({Views.BaseView.class})
public class ApiResponse<T> {
    private String message;
    private int statusCode;
    private T data;
}
