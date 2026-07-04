package com.shubham.job_tracker.dto.job;

import lombok.Builder;

import java.util.List;

@Builder
public record JobApplication(
        List<JobDetails> jobDetails,

        Long code,
        String message
) {
}
