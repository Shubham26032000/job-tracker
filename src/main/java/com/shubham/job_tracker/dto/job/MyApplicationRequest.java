package com.shubham.job_tracker.dto.job;

import lombok.Builder;

@Builder(toBuilder = true)
public record MyApplicationRequest(
        long userId,
        String sortBy,
        String sortOrder,
        String status
) {
}
