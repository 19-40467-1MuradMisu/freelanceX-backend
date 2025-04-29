package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.event.job.CreateJobEvent;
import com.freelancex.biddingservice.dtos.event.job.UpdateJobEvent;

public interface JobService {

    void createJob(CreateJobEvent event);

    void updateJob(UpdateJobEvent event);
}
