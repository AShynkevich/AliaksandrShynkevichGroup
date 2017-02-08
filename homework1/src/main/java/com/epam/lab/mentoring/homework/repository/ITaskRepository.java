package com.epam.lab.mentoring.homework.repository;

import com.epam.lab.mentoring.homework.domain.Task;

import java.util.List;

public interface ITaskRepository {
    boolean create(Task task);
    Task read(String taskId);
    Task update(Task task);
    boolean delete(String id);

    List<Task> findAll();
}
