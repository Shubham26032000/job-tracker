package com.shubham.job_tracker.controller;


import com.shubham.job_tracker.dto.user.*;
import com.shubham.job_tracker.exception.FailureException;
import com.shubham.job_tracker.service.UserService;
import com.shubham.job_tracker.utils.UserValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest userRequest) {
        CreateUserResponse response = userService.createUser(userRequest);
        if (response.code() != 200) {
            throw new FailureException(response.code(), response.message());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/")
    ResponseEntity<UpdateUserResponse> updateUser(@RequestBody UpdateUserRequest request){
        if (!UserValidationUtils.isValidUpdateUserRequest(request)){
            throw new FailureException(400L,"Invalid update request");
        }
        UpdateUserResponse response = userService.updateUser(request);
        if (response.code() != 200){
            throw new FailureException(response.code(),response.message());
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/")
    ResponseEntity<UserDetailsResponse> getUser(@RequestParam("userId") Long userId){
        UserDetailsResponse response = userService.getUser(userId);
        if (response.code() != 200){
            throw new FailureException(response.code(),response.message());
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsResponse>> getAllUsers(){
        List<UserDetailsResponse> userDetailsResponses =userService.getAllUsers();
        return new ResponseEntity<>(userDetailsResponses,HttpStatus.OK);
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }
}
