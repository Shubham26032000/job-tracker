package com.shubham.job_tracker.service;

import com.shubham.job_tracker.dto.job.*;

import java.util.List;

public interface JobService {
    JobResponse createJob(CreateJobRequest request);

    JobResponse updateJob(JobUpdateRequest request);

    JobDetails getJob(Long jobId);

    JobApplication getMyJobApplication(MyApplicationRequest request);

}
