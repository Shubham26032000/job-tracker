package com.shubham.job_tracker.security;

import com.shubham.job_tracker.entity.JobUser;
import com.shubham.job_tracker.repository.JobUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final JobUserRepository jobUserRepository;

    public UserDetailsServiceImpl(JobUserRepository jobUserRepository) {
        this.jobUserRepository = jobUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        JobUser jobUser = jobUserRepository.findByEmail(email).orElseThrow(() ->
        new UsernameNotFoundException("User not found with email :: " + email));
        return User.builder()
                .username(jobUser.getEmail())
                .password(jobUser.getPassword())
                .roles(jobUser.getRole())
//                .authorities(jobUser.getRole())
                .build();
    }
}
