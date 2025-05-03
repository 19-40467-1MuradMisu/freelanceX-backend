package com.freelancex.biddingservice.dtos.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.freelancex.biddingservice.views.Views;
import lombok.Builder;

@Builder
@JsonView({Views.BaseView.class})
public record ApiResponse<T>(String message, int statusCode, T data) {
}
