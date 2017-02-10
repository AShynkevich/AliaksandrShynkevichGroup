package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public class RemoveTaskConsoleInputHandler extends SafeConsoleInputHandler {
    @Override
    public void handleInput(BufferedReader br, ITaskService taskService) throws IOException {
        taskService.deleteTask(this.handleInput(br, "Set id to remove:>"));
    }
}
