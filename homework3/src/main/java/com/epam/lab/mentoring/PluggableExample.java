package com.epam.lab.mentoring;

import com.epam.lab.mentoring.api.IPluggable;

public class PluggableExample implements IPluggable {
    @Override
    public void executeCommand(String command) {
        System.out.println("Do nothing!");
    }
}
