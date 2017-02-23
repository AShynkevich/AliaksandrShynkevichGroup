package com.epam.lab.mentoring.console;

import com.epam.lab.mentoring.api.IPluggable;
import com.epam.lab.mentoring.extension.ExtensionRegistry;

import java.io.BufferedReader;
import java.io.IOException;

public class TestClassConsoleInputHandler extends SafeConsoleInputHandler {
    public TestClassConsoleInputHandler(BufferedReader br) { super(br); }

    @Override
    public void handleInput() throws IOException {
        String classId = this.handleInput("Provide class name:>");
        IPluggable pluggable = ExtensionRegistry.REGISTRY.getRegisteredClass(classId);
        if (null != pluggable) {
            pluggable.executeCommand("my command");
        } else {
            System.out.println("No class found!");
        }
    }
}
