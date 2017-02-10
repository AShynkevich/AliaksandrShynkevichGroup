package com.epam.lab.mentoring.homework;

import com.epam.lab.mentoring.homework.repository.FileTaskRepository;
import com.epam.lab.mentoring.homework.repository.ITaskRepository;

import static com.epam.lab.mentoring.homework.support.Constants.NO_REPOSITORY_FOUND_EXCEPTION;

public class TaskRepositoryFactory {
    public static ITaskRepository getTaskRepository(String resource) {
        switch(resource) {
            case "file": return new FileTaskRepository();
            default: throw new IllegalStateException(String.format(NO_REPOSITORY_FOUND_EXCEPTION, resource));
        }
    }
}
