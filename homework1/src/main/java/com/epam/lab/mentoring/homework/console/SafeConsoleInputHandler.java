package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.support.ConsoleUtils;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class SafeConsoleInputHandler implements IConsoleInputHandler {

    protected String handleInput(BufferedReader br, String inputMessage) throws IOException {
        String input;
        do {
            System.out.println(inputMessage);
        } while (!ConsoleUtils.validateInput(input = br.readLine()));

        return input;
    }
}
