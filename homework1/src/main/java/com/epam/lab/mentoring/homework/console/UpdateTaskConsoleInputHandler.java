package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public class UpdateTaskConsoleInputHandler extends StepTaskConsoleInputHandler {

    public UpdateTaskConsoleInputHandler(BufferedReader br, ITaskService taskService) {
        super(br, taskService);
    }

    @Override
    public void handleInput() throws IOException {
        taskService.updateTask(this.handleTaskCreationSteps());
    }
}
