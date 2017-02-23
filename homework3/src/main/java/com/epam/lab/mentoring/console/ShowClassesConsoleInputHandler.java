package com.epam.lab.mentoring.console;

import com.epam.lab.mentoring.extension.ExtensionRegistry;

import java.io.BufferedReader;
import java.io.IOException;

public class ShowClassesConsoleInputHandler extends SafeConsoleInputHandler {

    public ShowClassesConsoleInputHandler(BufferedReader br) {
        super(br);
    }

    @Override
    public void handleInput() throws IOException {
        System.out.println("Available plugins: " + ExtensionRegistry.REGISTRY.listAvailablePlugins());
    }
}
