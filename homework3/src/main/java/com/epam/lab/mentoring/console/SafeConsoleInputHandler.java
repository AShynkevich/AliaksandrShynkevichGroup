package com.epam.lab.mentoring.console;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class SafeConsoleInputHandler extends AbstractConsoleHandler implements IConsoleInputHandler {

    public SafeConsoleInputHandler(BufferedReader br) {
        super(br);
    }

    protected String handleInput(String inputMessage) throws IOException {
        String input;
        do {
            System.out.println("\n" + inputMessage);
        } while (!ConsoleUtils.validateInput(input = br.readLine()));

        return input;
    }

}
