package com.epam.lab.mentoring.homework.repository;

import com.epam.lab.mentoring.homework.domain.Task;
import com.epam.lab.mentoring.homework.support.AppConfig;
import com.epam.lab.mentoring.homework.support.FileRepositoryUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static com.epam.lab.mentoring.homework.support.Constants.TASK_FILE_KEY;

public class FileTaskRepository implements ITaskRepository {

    private static final String FILE = AppConfig.INSTANCE.getProperty(TASK_FILE_KEY);

    @Override
    public boolean create(Task task) {
        List<Task> tasks = FileRepositoryUtils.readListFromFile(FILE);

        if (tasks.stream().anyMatch(storedTask -> task.getId().equals(storedTask.getId()))) {
            // log with info about
            return false;
        }

        tasks.add(task);

        FileRepositoryUtils.writeListToFile(tasks, FILE);
        return true;
    }

    @Override
    public Task read(String taskId) {
        List<Task> tasks = FileRepositoryUtils.readListFromFile(FILE);

        if (CollectionUtils.isNotEmpty(tasks)) {
            return tasks.stream()
                    .filter(task -> taskId.equals(task.getId()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public Task update(Task task) {
        List<Task> tasks = FileRepositoryUtils.readListFromFile(FILE);

        if (CollectionUtils.isNotEmpty(tasks)) {
            for (Task stored : tasks) {
                if (task.getId().equals(stored.getId())) {
                    stored.setDate(task.getDate());
                    stored.setDescription(task.getDescription());
                    stored.setName(task.getName());
                    break;
                }
            }
        } else {
            tasks.add(task);
        }

        FileRepositoryUtils.writeListToFile(tasks, FILE);

        return task;
    }

    @Override
    public boolean delete(String id) {
        List<Task> tasks = FileRepositoryUtils.readListFromFile(FILE);

        if (tasks.stream().anyMatch(task -> id.equals(task.getId()))) {
            // add logging
            return false;
        }
        tasks.removeIf(task -> id.equals(task.getId()));
        FileRepositoryUtils.writeListToFile(tasks, FILE);
        return true;

    }

    @Override
    public List<Task> findAll() {
        return FileRepositoryUtils.readListFromFile(FILE);
    }
}
