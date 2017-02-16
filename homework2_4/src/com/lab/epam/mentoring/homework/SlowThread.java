package com.lab.epam.mentoring.homework;

public class SlowThread extends Thread {

    private boolean withGlobal;

    public SlowThread(boolean withGlobal) {
        this.withGlobal = withGlobal;
    }

    @Override
    public void run() {
        if (withGlobal) {
            globalLock();
        } else {
            localLock();
        }
    }

    // locks object and goes to sleep
    private void globalLock() {
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

    // locks object only when needed
    private void localLock() {
        try {
            SlowThread.sleep(10000); // object is locked but thread does nothing for 10 seconds
            synchronized (StatefulObject.INSTANCE) {
                System.out.println("Slow thread woke up and got object.");
                StatefulObject.INSTANCE.writeState("State is written with local lock.");
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }
}
