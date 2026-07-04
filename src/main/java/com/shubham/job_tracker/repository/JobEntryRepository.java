package com.shubham.job_tracker.repository;

import com.shubham.job_tracker.entity.JobEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobEntryRepository extends JpaRepository<JobEntry,Long> {

}
