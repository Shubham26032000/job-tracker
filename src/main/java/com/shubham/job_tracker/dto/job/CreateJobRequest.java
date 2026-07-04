package com.shubham.job_tracker.dto.job;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateJobRequest(
        @NotNull
        Long userId,
        @NotEmpty
        String companyName,
        @NotEmpty
        String role,
        String jobDescription,
        String jobLink,
        String personalNotes
) {
}
