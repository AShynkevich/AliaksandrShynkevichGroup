package com.lab.epam.mentoring;

import com.lab.epam.mentoring.homework.FastThread;
import com.lab.epam.mentoring.homework.SlowThread;
import com.lab.epam.mentoring.homework.StatefulObject;

public class Main {

    private static final int FAST_THREADS_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {
        StatefulObject.INSTANCE.writeState("Initial state");

        // sleep time used to connect to java process in jvisualvm if needed
        Thread.sleep(10000);
        for (int i = 0; i < FAST_THREADS_COUNT; i++) {
            new FastThread(i).start(); // run couple of fast thread
        }

        // start slow thread that locks and goes to sleep
        SlowThread slowThread1 = new SlowThread(true);
        slowThread1.start();

        // no threads can proceed until slowThread1 frees the object
        for (int i = 0; i < FAST_THREADS_COUNT; i++) {
            new FastThread(i).start();
        }

        // start slow thread that locks only when needed
        SlowThread slowThread2 = new SlowThread(false);
        slowThread2.start();
        for (int i = 0; i < FAST_THREADS_COUNT; i++) {
            new FastThread(i).start();
        }
    }

}
