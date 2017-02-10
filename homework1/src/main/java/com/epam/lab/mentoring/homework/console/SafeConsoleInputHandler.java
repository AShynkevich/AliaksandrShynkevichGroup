package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;
import com.epam.lab.mentoring.homework.support.ConsoleUtils;
import org.joda.time.LocalDateTime;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class SafeConsoleInputHandler extends AbstractConsoleHandler implements IConsoleInputHandler {

    public SafeConsoleInputHandler(BufferedReader br, ITaskService taskService) {
        super(br, taskService);
    }

    protected String handleInput(String inputMessage) throws IOException {
        String input;
        do {
            System.out.println(inputMessage);
        } while (!ConsoleUtils.validateInput(input = br.readLine()));

        return input;
    }

    protected LocalDateTime handleDateInput(String inputMessage) throws IOException {
        LocalDateTime input = null;
        while (null == input) {
            input = ConsoleUtils.parseDate(this.handleInput(inputMessage));
        }
        return input;
    }
}
