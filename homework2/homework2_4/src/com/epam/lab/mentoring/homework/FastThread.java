package com.epam.lab.mentoring.homework;

public class FastThread extends Thread {

    @Override
    public void run() {
        synchronized (StatefulObject.INSTANCE) {
            System.out.println("Fast thread got resource. Fast read: " + StatefulObject.INSTANCE.readState());
        }
    }
}
