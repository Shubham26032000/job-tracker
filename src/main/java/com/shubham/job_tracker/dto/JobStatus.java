package com.shubham.job_tracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum JobStatus {
    APPLIED("Applied"),
    INTERVIEW_SCHEDULED("Interview Scheduled"),
    UNDER_REVIEW("Under Review"),
    OFFERED("Offered"),
    REJECTED("Rejected");

    private final String displayName;
    JobStatus(String displayName) {
        this.displayName = displayName;
    }
}
