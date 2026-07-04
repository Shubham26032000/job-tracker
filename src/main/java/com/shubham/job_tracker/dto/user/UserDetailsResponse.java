package com.shubham.job_tracker.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder(toBuilder = true)
public record UserDetailsResponse(
        Long userId,
        String firstName,
        String lastName,
        String email,

        long code,
        String message

) {
}
