package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.support.ConsoleUtils;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class SafeConsoleInputHandler implements IConsoleInputHandler {

    protected String handleInput(BufferedReader br, String inputMessage) throws IOException {
        System.out.println(inputMessage);
        String input;
        while(!ConsoleUtils.validateInput(input = br.readLine())) {}

        return input;
    }
}
