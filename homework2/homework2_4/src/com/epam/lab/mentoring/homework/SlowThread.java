package com.epam.lab.mentoring.homework;

public class SlowThread extends Thread {

    @Override
    public void run() {
        synchronized (StatefulObject.INSTANCE) {
            System.out.println("Slow thread got object.");
            try {
                SlowThread.sleep(10000); // object is locked but thread does nothing for 10 seconds
                System.out.println("Slow thread woke up after hibernation.");
                StatefulObject.INSTANCE.writeState("State is written global lock.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
