package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.domain.Task;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class StepTaskConsoleInputHandler extends SafeConsoleInputHandler {
    protected Task handeTaskCreationSteps(BufferedReader br) throws IOException {
        Task task = new Task();

        task.setId(this.handleInput(br, "Set task id:>"));
        task.setName(this.handleInput(br, "Set task name:>"));
        task.setDescription(this.handleInput(br, "Set task description:>"));
        task.setDate(this.handleDateInput(br, "Set task date:>"));

        return task;
    }
}
