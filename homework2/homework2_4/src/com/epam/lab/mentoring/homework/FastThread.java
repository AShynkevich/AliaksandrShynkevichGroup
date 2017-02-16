package com.epam.lab.mentoring.homework;

public class FastThread extends Thread {

    private int index;
    public FastThread(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        synchronized (StatefulObject.INSTANCE) {
            System.out.println("Fast [" + index + "] got resource. Fast [" + index + "] read: "
                    + StatefulObject.INSTANCE.readState());
        }
    }
}
