package com.epam.lab.mentoring.homework.service;

import com.epam.lab.mentoring.homework.domain.Task;
import com.epam.lab.mentoring.homework.repository.ITaskRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class SimpleTaskService implements ITaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTaskService.class);

    private ITaskRepository taskRepository;

    public SimpleTaskService() {}

    public SimpleTaskService(ITaskRepository repository) {
        taskRepository = repository;
    }

    @Override
    public void createTask(Task task) {
        if (!taskRepository.create(task)) {
            LOGGER.info("Failed to create task. The same task possibly already exist!");
        }
    }

    @Override
    public Task readTask(String taskId) {
        Task task = taskRepository.read(taskId);

        if (null == task) {
            LOGGER.info("No task with id [{}] found!", taskId);
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
            LOGGER.info("Failed to delete task with id [{}]. The task with such id might possibly not exists!", id);
        }
    }

    @Override
    public List<Task> findTasks() {
        List<Task> tasks = taskRepository.findAll();

        if (CollectionUtils.isEmpty(tasks)) {
            LOGGER.info("No tasks yet!");
            return Collections.emptyList();
        } else {
            return tasks;
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
