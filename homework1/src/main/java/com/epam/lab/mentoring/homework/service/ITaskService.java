package com.epam.lab.mentoring.homework.service;

import com.epam.lab.mentoring.homework.domain.Task;
import com.epam.lab.mentoring.homework.repository.ITaskRepository;

import java.util.List;

public interface ITaskService {
    void createTask(Task task);
    Task readTask(String taskId);
    Task updateTask(Task task);
    void deleteTask(String id);

    List<Task> findTasks();

    // pseudo builder
    ITaskService withRepository(ITaskRepository repository);
}
