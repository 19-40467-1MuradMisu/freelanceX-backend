package com.freelancex.ratingservice.services.interfaces;

import com.freelancex.ratingservice.dtos.event.job.CreateJobEvent;
import com.freelancex.ratingservice.dtos.event.job.updateJobEvent;

public interface JobService {
    void createJob(CreateJobEvent event);
    void updateJob(updateJobEvent event);
}
