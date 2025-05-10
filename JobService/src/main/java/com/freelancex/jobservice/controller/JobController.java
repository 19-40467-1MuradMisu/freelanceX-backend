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
    public ResponseEntity<ApiResponse<Job>> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        ApiResponse<Job> response = new ApiResponse<>("Job created successfully", HttpStatus.CREATED.value(), createdJob);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Job>>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        ApiResponse<List<Job>> response = new ApiResponse<>("Jobs fetched successfully", HttpStatus.OK.value(), jobs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Job>> getJobById(@PathVariable UUID id) {
        Job job = jobService.getJobById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        ApiResponse<Job> response = new ApiResponse<>("Job fetched successfully", HttpStatus.OK.value(), job);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/client")
    public ResponseEntity<ApiResponse<Job>> updateJob(@PathVariable UUID id, @RequestBody Job jobDetails) {
        Job updatedJob = jobService.updateJob(id, jobDetails);
        ApiResponse<Job> response = new ApiResponse<>("Job updated successfully", HttpStatus.OK.value(), updatedJob);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/client")
    public ResponseEntity<ApiResponse<Void>> deleteJob(@PathVariable UUID id) {
        jobService.deleteJob(id);
        ApiResponse<Void> response = new ApiResponse<>("Job deleted successfully", HttpStatus.OK.value(), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
