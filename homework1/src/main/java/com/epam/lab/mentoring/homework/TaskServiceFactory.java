package com.epam.lab.mentoring.homework;

import com.epam.lab.mentoring.homework.service.ITaskService;
import com.epam.lab.mentoring.homework.service.SimpleTaskService;

import static com.epam.lab.mentoring.homework.support.Constants.NO_SERVICE_FOUND_EXCEPTION;

public class TaskServiceFactory {

    public static ITaskService getTaskService(String type) {
        switch (type) {
            case "simple": return new SimpleTaskService();
            default: throw new IllegalStateException(String.format(NO_SERVICE_FOUND_EXCEPTION, type));
        }
    }

}
