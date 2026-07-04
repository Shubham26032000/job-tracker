package com.shubham.job_tracker.dto.job;

import lombok.Builder;

@Builder
public record JobResponse(
        Long jobId,
        Long code,
        String status,
        String message
) {
}
