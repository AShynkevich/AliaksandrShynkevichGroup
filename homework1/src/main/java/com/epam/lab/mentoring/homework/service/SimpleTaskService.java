package com.epam.lab.mentoring.homework.service;

import com.epam.lab.mentoring.homework.domain.Task;
import com.epam.lab.mentoring.homework.repository.ITaskRepository;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class SimpleTaskService implements ITaskService {

    private ITaskRepository taskRepository;

    public SimpleTaskService() {}

    public SimpleTaskService(ITaskRepository repository) {
        taskRepository = repository;
    }

    @Override
    public void createTask(Task task) {
        if (!taskRepository.create(task)) {
            // TODO: switch to logger
            System.out.println("Failed to create task. The same task possibly already exist!");
        }
    }

    @Override
    public Task readTask(String taskId) {
        Task task = taskRepository.read(taskId);

        if (null == task) {
            System.out.println("Not task with id [".concat(taskId).concat("] found!"));
        }

        return task;
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public void deleteTask(String id) {
        if (!taskRepository.delete(id)) {
            System.out.println("Failed to delete task. The task with that id possibly does not exist!");
        }
    }

    @Override
    public void showTasks() {
        List<Task> tasks = taskRepository.findAll();

        if (CollectionUtils.isEmpty(tasks)) {
            System.out.println("No tasks yet!");
        } else {
            System.out.println("Current tasks:>");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    @Override
    public ITaskService withRepository(ITaskRepository repository) {
        taskRepository = repository;
        return this;
    }

    public ITaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
