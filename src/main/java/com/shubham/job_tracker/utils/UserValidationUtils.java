package com.shubham.job_tracker.utils;

import ch.qos.logback.core.util.StringUtil;
import com.shubham.job_tracker.dto.user.UpdateUserRequest;

public class UserValidationUtils {

    public static boolean isValidUpdateUserRequest(UpdateUserRequest request) {
        if (request != null && (StringUtil.notNullNorEmpty(request.firstName())
                || StringUtil.notNullNorEmpty(request.lastName())
                || StringUtil.notNullNorEmpty(request.password())
        )) {
            return true;
        }

        return false;
    }
}
