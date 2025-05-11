package com.freelancex.jobservice.controller;

import com.freelancex.jobservice.dtos.common.ApiResponse;
import com.freelancex.jobservice.models.Job;
import com.freelancex.jobservice.services.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobServiceImpl jobService;

    @Autowired
    public JobController(JobServiceImpl jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/client")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable UUID id) {
        Job job = jobService.getJobById(id);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PutMapping("/{id}/client/{clientId}")
    public ResponseEntity<Job> updateJob(@PathVariable UUID id, @PathVariable UUID clientId,
                                                      @RequestBody Job jobDetails) {
        Job updatedJob = jobService.updateJob(id, clientId, jobDetails);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/client/{clientId}")
    public ResponseEntity<Void> deleteJob(@PathVariable UUID id, @PathVariable UUID clientId) {
        jobService.deleteJob(id, clientId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
