package com.shubham.job_tracker.repository;

import com.shubham.job_tracker.entity.JobUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobUserRepository extends JpaRepository<JobUser,Long> {

    Optional<JobUser> findByUserId(Long userId);

    Optional<JobUser> findByEmail(String email);

}
