package com.epam.lab.mentoring.homework;

import com.epam.lab.mentoring.homework.service.ITaskService;
import com.epam.lab.mentoring.homework.service.SimpleTaskService;

public class TaskServiceFactory {
    public static ITaskService getTaskService() {
        return new SimpleTaskService();
    }
}
