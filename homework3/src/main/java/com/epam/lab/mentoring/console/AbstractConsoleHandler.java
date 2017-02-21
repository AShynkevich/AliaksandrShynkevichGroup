package com.epam.lab.mentoring.console;

import java.io.BufferedReader;

public abstract class AbstractConsoleHandler {
    protected BufferedReader br;

    public AbstractConsoleHandler(BufferedReader br) {
        this.br = br;
    }
}
