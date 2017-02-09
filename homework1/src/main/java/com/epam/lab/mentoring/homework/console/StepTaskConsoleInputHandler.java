package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.domain.Task;
import org.joda.time.LocalDateTime;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class StepTaskConsoleInputHandler extends SafeConsoleInputHandler {

    protected Task handeTaskCreationSteps(BufferedReader br) throws IOException {
        Task task = new Task();

        task.setId(this.handleInput(br, "Set task id:>"));
        task.setName(this.handleInput(br, "Set task name:>"));
        task.setDescription(this.handleInput(br, "Set task description:>"));

        // need special care
        while (true) {
            try {
                task.setDate(LocalDateTime.parse(this.handleInput(br, "Set task date:>")));
                break;
            } catch (IllegalArgumentException exc) {
                System.out.println("Incorrect date format!");
            }
        }

        return task;
    }

}
