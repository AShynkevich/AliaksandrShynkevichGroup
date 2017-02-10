package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public class FindTaskConsoleInputHandler extends SafeConsoleInputHandler {

    public FindTaskConsoleInputHandler(BufferedReader br, ITaskService taskService) {
        super(br, taskService);
    }

    @Override
    public void handleInput() throws IOException {
        taskService.readTask(this.handleInput("Set task id to find:>"));
    }
}
