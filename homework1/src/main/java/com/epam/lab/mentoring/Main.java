package com.epam.lab.mentoring;

import com.epam.lab.mentoring.homework.TaskConsoleApplication;

public class Main {

    // possible options -DtaskFile=app.txt
    public static void main(String[] args) {
        TaskConsoleApplication app = new TaskConsoleApplication();
        app.run();
    }

}
