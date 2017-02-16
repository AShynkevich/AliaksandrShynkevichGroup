package com.epam.lab.mentoring.homework;

public enum StatefulObject {
    INSTANCE;

    private String state = "Initial state";

    public void writeState(String state) {
        this.state = state;
    }

    public String readState() {
        return state;
    }
}
