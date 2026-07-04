package com.shubham.job_tracker.utils;
import ch.qos.logback.core.util.StringUtil;
import com.shubham.job_tracker.dto.job.JobUpdateRequest;
import com.shubham.job_tracker.dto.user.UpdateUserRequest;

public class JobValidationUtils {

    public static boolean isValidJobUpdateRequest(JobUpdateRequest request){
        if (request != null &&
                (StringUtil.notNullNorEmpty(request.jobLink())) ||
                (StringUtil.notNullNorEmpty(request.description())) ||
                (StringUtil.notNullNorEmpty(request.status())) ||
                (StringUtil.notNullNorEmpty(request.personalNote())) ||
                (StringUtil.notNullNorEmpty(request.role())) ||
                (StringUtil.notNullNorEmpty(request.company()
                ))
        ){
            return true;
        }

        return false;
    }
}
