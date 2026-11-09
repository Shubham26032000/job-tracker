package com.shubham.job_tracker.service;

import ch.qos.logback.core.util.StringUtil;
import com.shubham.job_tracker.dto.user.*;
import com.shubham.job_tracker.entity.JobEntry;
import com.shubham.job_tracker.entity.JobUser;
import com.shubham.job_tracker.repository.JobUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final JobUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(JobUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        JobUser jobUser = JobUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("USER")
                .build();
        try {
            JobUser user = repository.save(jobUser);

            return CreateUserResponse.builder()
                    .userEmail(user.getEmail())
                    .userId(user.getUserId())
                    .code(200L)
                    .message("User created successfully")
                    .status("SUCCESS")
                    .build();
        } catch (Exception exception) {
            log.error("Exception occurred while creating user {}", exception.getMessage());
            exception.printStackTrace();
            return CreateUserResponse.builder()
                    .code(500L)
                    .message(exception.getMessage())
                    .build();
        }
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        try {
            Optional<JobUser> jobUserOptional = repository.findByUserId(request.userId());
            if (jobUserOptional.isEmpty()) {
                return UpdateUserResponse.builder()
                        .code(404L)
                        .message("User Not found with ID : " + request.userId())
                        .build();
            }

            JobUser user = jobUserOptional.get();
            if (!StringUtil.isNullOrEmpty(request.firstName())) {
                user = user.toBuilder().firstName(request.firstName()).build();
            }
            if (!StringUtil.isNullOrEmpty(request.lastName())) {
                user = user.toBuilder().lastName(request.lastName()).build();
            }
            user = repository.save(user);

            return UpdateUserResponse.builder()
                    .userId(user.getUserId())
                    .code(200L)
                    .status("SUCCESS")
                    .message("User Updated successfully")
                    .build();
        } catch (Exception exception) {
            log.error("Exception occurred while creating user {}", exception.getMessage());
            exception.printStackTrace();
            return UpdateUserResponse.builder()
                    .code(500L)
                    .message(exception.getMessage())
                    .build();
        }
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @Override
    public UserDetailsResponse getUser(Long userId) {
        Optional<JobUser> optionalJobUser = repository.findByUserId(userId);

        if (optionalJobUser.isEmpty()) {
            log.error("User not found with id : {}", userId);
            return UserDetailsResponse.builder()
                    .code(404L)
                    .message("User not found with id : " + userId)
                    .build();
        }

        JobUser jobUser = optionalJobUser.get();

        return UserDetailsResponse.builder()
                .userId(userId)
                .firstName(jobUser.getFirstName())
                .lastName(jobUser.getLastName())
                .email(jobUser.getEmail())
                .code(200L)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserDetailsResponse> getAllUsers() {
        List<JobUser> jobUsers = repository.findAll();
        return jobUsers.stream()
                .map(user -> UserDetailsResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())
                .toList();
    }
}
