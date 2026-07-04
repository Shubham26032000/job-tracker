package com.shubham.job_tracker.dto.user;


import lombok.Builder;

@Builder(toBuilder = true)
public record UpdateUserResponse(
        Long userId,
        Long code,
        String status,
        String message
) {
}
