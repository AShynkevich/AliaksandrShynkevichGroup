package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.domain.Task;
import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class StepTaskConsoleInputHandler extends SafeConsoleInputHandler {

    public StepTaskConsoleInputHandler(BufferedReader br, ITaskService taskService) {
        super(br, taskService);
    }

    protected Task handleTaskCreationSteps() throws IOException {
        Task task = new Task();

        task.setId(this.handleInput("Set task id:>"));
        task.setName(this.handleInput("Set task name:>"));
        task.setDescription(this.handleInput("Set task description:>"));
        task.setDate(this.handleDateInput("Set task date (yyyy, yyyy-mm-dd, yyyy-mm, yyyy-mm-ddThh-mm) :>"));

        return task;
    }
}
