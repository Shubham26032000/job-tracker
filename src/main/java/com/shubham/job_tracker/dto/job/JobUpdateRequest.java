package com.shubham.job_tracker.dto.job;

public record JobUpdateRequest(
        Long jobId,
        String company,
        String description,
        String personalNote,
        String jobLink,
        String role,
        String status
) {
}
