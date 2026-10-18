package com.shubham.job_tracker.controller;


import com.shubham.job_tracker.dto.job.*;
import com.shubham.job_tracker.exception.FailureException;
import com.shubham.job_tracker.service.JobService;
import com.shubham.job_tracker.utils.JobValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @PostMapping("/")
    ResponseEntity<JobResponse> createJob(@RequestBody CreateJobRequest request) {
        JobResponse response = service.createJob(request);
        if (response.code() != 200) {
            throw new FailureException(response.code(), response.message());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/")
    ResponseEntity<JobResponse> updateJob(@RequestBody JobUpdateRequest request) {

        if (!JobValidationUtils.isValidJobUpdateRequest(request)) {
            throw new FailureException(400L, "Invalid job request");
        }
        JobResponse response = service.updateJob(request);
        if (response.code() != 200) {
            throw new FailureException(response.code(), response.message());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<JobDetails> getJob(@RequestParam("jobId") Long jobId) {
        JobDetails jobDetails = service.getJob(jobId);
        if (jobDetails.code() != 200) {
            throw new FailureException(jobDetails.code(), jobDetails.message());
        }

        return new ResponseEntity<>(jobDetails, HttpStatus.OK);
    }

    @GetMapping("/applications")
    ResponseEntity<JobApplication> getMyJob(@RequestBody MyApplicationRequest request) {
        JobApplication jobApplication = service.getMyJobApplication(request);

        if (jobApplication.code() != 200) {
            throw new FailureException(jobApplication.code(), jobApplication.message());
        }

        return new ResponseEntity<>(jobApplication, HttpStatus.OK);
    }

    @GetMapping("/current/user")
    public String currentUser(Authentication authentication){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Name "+authentication.getName());
        System.out.println("getAuthorities "+authentication.getAuthorities());
        System.out.println("getCredentials "+authentication.getCredentials());
        System.out.println("getDetails "+authentication.getDetails());
        System.out.println("getPrincipal "+authentication.getPrincipal());
        return "logged om user : "+authentication.getName();
    }


}
