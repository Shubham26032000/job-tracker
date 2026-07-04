package com.shubham.job_tracker.dto.user;

public record UpdateUserRequest(
        Long userId,
        String firstName,
        String lastName,
        String password
) {
}
