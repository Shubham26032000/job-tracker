package com.shubham.job_tracker.dto.user;

import lombok.Builder;

@Builder(toBuilder = true)
public record CreateUserResponse(
        Long userId,
        String userEmail,
        Long code,
        String status,
        String message
) {
}
