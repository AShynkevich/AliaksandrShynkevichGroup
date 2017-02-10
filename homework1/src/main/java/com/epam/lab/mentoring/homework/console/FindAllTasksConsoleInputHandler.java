package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public class FindAllTasksConsoleInputHandler extends SafeConsoleInputHandler{
    @Override
    public void handleInput(BufferedReader br, ITaskService taskService) throws IOException {
        taskService.findTasks().forEach(System.out::println);
    }
}
