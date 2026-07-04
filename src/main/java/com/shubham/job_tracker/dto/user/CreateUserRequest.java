package com.shubham.job_tracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder(toBuilder = true)
public record CreateUserRequest(
        @Email
        @NotEmpty
        String email,
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @NotEmpty
        String password
) {
}
