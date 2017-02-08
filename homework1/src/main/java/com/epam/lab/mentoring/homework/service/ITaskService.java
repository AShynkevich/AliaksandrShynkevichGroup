package com.epam.lab.mentoring.homework.service;

import com.epam.lab.mentoring.homework.domain.Task;
import com.epam.lab.mentoring.homework.repository.ITaskRepository;

public interface ITaskService {

    void createTask(Task task);
    Task readTask(String taskId);
    Task updateTask(Task task);
    void deleteTask(String id);

    void showTasks();

    // pseudo builder
    ITaskService withRepository(ITaskRepository repository);
}
