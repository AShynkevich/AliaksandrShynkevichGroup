package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;

import java.io.BufferedReader;

public abstract class AbstractConsoleHandler {
    protected BufferedReader br;
    protected ITaskService taskService;

    public AbstractConsoleHandler(BufferedReader br, ITaskService taskService) {
        this.br = br;
        this.taskService = taskService;
    }

}
