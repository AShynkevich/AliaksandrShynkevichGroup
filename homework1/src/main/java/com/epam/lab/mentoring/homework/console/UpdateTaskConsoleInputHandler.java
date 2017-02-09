package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public class UpdateTaskConsoleInputHandler extends StepTaskConsoleInputHandler {
    @Override
    public void handleInput(BufferedReader br, ITaskService taskService) throws IOException {
        taskService.updateTask(this.handeTaskCreationSteps(br));
    }
}
