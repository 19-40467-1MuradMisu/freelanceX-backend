package com.freelancex.biddingservice.dtos.event.job;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteJobEvent (@NotNull UUID jobId){}