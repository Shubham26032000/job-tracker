package com.shubham.job_tracker.repository;

import com.shubham.job_tracker.entity.JobEntry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobEntryRepository extends JpaRepository<JobEntry,Long> {

    List<JobEntry> findAllByUserUserId(Long userId, Sort sort);

    List<JobEntry> findAllByUserUserIdAndStatus(Long userId, String status, Sort sort);
}
