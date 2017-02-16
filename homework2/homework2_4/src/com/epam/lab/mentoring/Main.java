package com.epam.lab.mentoring;

import com.epam.lab.mentoring.homework.FastThread;
import com.epam.lab.mentoring.homework.SlowThread;
import com.epam.lab.mentoring.homework.StatefulObject;

public class Main {

    private static final int FAST_THREADS_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {
        StatefulObject.INSTANCE.writeState("Initial state");

        // start slow thread that locks and goes to sleep
        SlowThread slowThread1 = new SlowThread();
        slowThread1.start();

        // no threads can proceed until slowThread1 frees the object
        for (int i = 0; i < FAST_THREADS_COUNT; i++) {
            new FastThread().start();
        }
    }

}
