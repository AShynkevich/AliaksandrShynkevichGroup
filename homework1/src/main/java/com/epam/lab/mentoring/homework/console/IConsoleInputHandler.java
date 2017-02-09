package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;
import java.io.IOException;

public interface IConsoleInputHandler {

    void handleInput(BufferedReader br, ITaskService taskService) throws IOException;
}
