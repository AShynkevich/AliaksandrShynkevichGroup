package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public class FindTaskConsoleInputHandler extends SafeConsoleInputHandler {
    @Override
    public void handleInput(BufferedReader br, ITaskService taskService) throws IOException {
        taskService.readTask(this.handleInput(br, "Set task id to find:>"));
    }
}
