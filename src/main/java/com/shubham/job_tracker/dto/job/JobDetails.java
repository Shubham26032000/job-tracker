package com.shubham.job_tracker.dto.job;

import lombok.Builder;

import java.util.Date;

@Builder(toBuilder = true)
public record JobDetails(
        Long jobId,
        String companyName,
        String role,
        String description,
        String jobLink,
        String personalNote,
        String applicationStatus,
        Date dateOfApplication,
        Date lastModified,

        Long code,
        String message

) {
}
