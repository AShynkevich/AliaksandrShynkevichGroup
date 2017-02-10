package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public class FindAllTasksConsoleInputHandler extends SafeConsoleInputHandler{

    public FindAllTasksConsoleInputHandler(BufferedReader br, ITaskService taskService) {
        super(br, taskService);
    }

    @Override
    public void handleInput() throws IOException {
        taskService.findTasks().forEach(System.out::println);
    }
}
