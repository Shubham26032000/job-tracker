package com.shubham.job_tracker.service;

import com.shubham.job_tracker.dto.user.*;

import java.util.List;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);

    UpdateUserResponse updateUser(UpdateUserRequest request);

    UserDetailsResponse getUser(Long userId);

    List<UserDetailsResponse> getAllUsers();
}
