package com.shubham.job_tracker.service;

import ch.qos.logback.core.util.StringUtil;
import com.shubham.job_tracker.dto.JobStatus;
import com.shubham.job_tracker.dto.job.*;
import com.shubham.job_tracker.entity.JobEntry;
import com.shubham.job_tracker.entity.JobUser;
import com.shubham.job_tracker.repository.JobEntryRepository;
import com.shubham.job_tracker.repository.JobUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobEntryRepository repository;
    private final JobUserRepository userRepository;

    public JobServiceImpl(JobEntryRepository repository, JobUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public JobResponse createJob(CreateJobRequest request) {
        Optional<JobUser> jobUserOptional = userRepository.findByUserId(request.userId());
        if (jobUserOptional.isEmpty()){
            return JobResponse.builder()
                    .message("Invalid user id : "+request.userId()+" No user found")
                    .code(404L)
                    .build();
        }

        JobUser user = jobUserOptional.get();
        Date now = new Date();
        log.info("Current date : {}",now);
        JobEntry jobEntry = JobEntry.builder()
                .companyName(request.companyName())
                .url(request.jobLink())
                .status(JobStatus.APPLIED.getDisplayName())
                .roleApplied(request.role())
                .personalNote(request.personalNotes())
                .description(request.jobDescription())
                .dateOfApplication(now)
                .lastModified(now)
                .user(user)
                .build();

        try {
            jobEntry = repository.save(jobEntry);
            return JobResponse.builder()
                    .jobId(jobEntry.getJobId())
                    .status("SUCCESS")
                    .code(200L)
                    .message("Job Application saved successfully!")
                    .build();
        } catch (Exception exception) {
            log.error("Exception occurred while saving the job application : {}", exception.getMessage());
            exception.printStackTrace();
            return JobResponse.builder()
                    .message(exception.getMessage())
                    .code(500L)
                    .build();
        }
    }

    @Transactional
    @Override
    public JobResponse updateJob(JobUpdateRequest request) {
        Optional<JobEntry> jobEntryOptional = repository.findById(request.jobId());
        if (jobEntryOptional.isEmpty()){
            return JobResponse.builder()
                    .code(404L)
                    .message("Job not found with id "+request.jobId())
                    .build();
        }
        JobEntry jobEntry = jobEntryOptional.get();
        if (!StringUtil.isNullOrEmpty(request.company())) {
            jobEntry = jobEntry.toBuilder().companyName(request.company()).build();
        }
        if (!StringUtil.isNullOrEmpty(request.role())){
            jobEntry = jobEntry.toBuilder().roleApplied(request.role()).build();
        }
        if (!StringUtil.isNullOrEmpty(request.jobLink())){
            jobEntry = jobEntry.toBuilder().url(request.jobLink()).build();
        }
        if (!StringUtil.isNullOrEmpty(request.personalNote())){
            jobEntry = jobEntry.toBuilder().personalNote(request.personalNote()).build();
        }
        if (!StringUtil.isNullOrEmpty(request.description())){
            jobEntry = jobEntry.toBuilder().description(request.description()).build();
        }

        if (!StringUtil.isNullOrEmpty(request.status())){
            jobEntry = jobEntry.toBuilder().status(getJobStatus(request.status())).build();
        }

        jobEntry = jobEntry.toBuilder().lastModified(new Date()).build();
        try {
            jobEntry = repository.save(jobEntry);
        }catch (Exception exception){
            log.error("Exception occured while updating the job application :  {}",exception.getMessage());
            return JobResponse
                    .builder()
                    .code(500L)
                    .message(exception.getMessage())
                    .build();
        }
        return JobResponse
                .builder()
                .jobId(jobEntry.getJobId())
                .status(jobEntry.getStatus())
                .code(200L)
                .build();
    }

    private String getJobStatus(String status) {
        try {

            return JobStatus.valueOf(status.toUpperCase()).getDisplayName();
        } catch (IllegalArgumentException | NullPointerException e) {
            throw e;
        }
    }

    @Override
    public JobDetails getJob(Long jobId) {
        Optional<JobEntry> jobDetailsOptional = repository.findById(jobId);
        if (jobDetailsOptional.isEmpty()) {
            return JobDetails.builder()
                    .code(404L)
                    .message("Job Not Found !")
                    .build();
        }
        JobEntry jobEntry = jobDetailsOptional.get();
        return JobDetails.builder()
                .code(200L)
                .jobId(jobId)
                .companyName(jobEntry.getCompanyName())
                .jobLink(jobEntry.getUrl())
                .description(jobEntry.getDescription())
                .role(jobEntry.getRoleApplied())
                .personalNote(jobEntry.getPersonalNote())
                .dateOfApplication(jobEntry.getDateOfApplication())
                .lastModified(jobEntry.getLastModified())
                .applicationStatus(jobEntry.getStatus())
                .build();
    }

    @Transactional
    @Override
    public JobApplication getMyJobApplication(MyApplicationRequest request) {
        try {
            List<JobEntry> jobEntryList = jobApplicationHelper(request);

            List<JobDetails> jobDetailsEntries = jobEntryList.stream().map(application -> JobDetails.builder()
                    .jobId(application.getJobId())
                    .companyName(application.getCompanyName())
                    .description(application.getDescription())
                    .jobLink(application.getUrl())
                    .role(application.getRoleApplied())
                    .dateOfApplication(application.getDateOfApplication())
                    .lastModified(application.getLastModified())
                    .personalNote(application.getPersonalNote())
                    .applicationStatus(application.getStatus())
                    .build()
            ).toList();

            return JobApplication.builder()
                    .code(200L)
                    .jobDetails(jobDetailsEntries)
                    .build();
        } catch (Exception exception) {
            log.error("Exception while fetching jobEntries : {}", exception.getMessage());
            exception.printStackTrace();
            return JobApplication.builder()
                    .code(500L)
                    .message(exception.getMessage())
                    .build();
        }

    }

    private List<JobEntry> jobApplicationHelper(MyApplicationRequest request){
        Sort sort = null;
        if (request.sortOrder() != null && request.sortOrder().equalsIgnoreCase("asc")){
            sort = Sort.by("dateOfApplication").ascending();
        }else {
            sort = Sort.by("dateOfApplication").descending();
        }
        if(request.status() != null){
            return repository.findAllByUserUserIdAndStatus(request.userId(),request.status(),sort);
        }
        return repository.findAllByUserUserId(request.userId(),sort);
    }
}
