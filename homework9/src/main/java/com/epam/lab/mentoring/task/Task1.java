package com.epam.lab.mentoring.task;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

// Create several instances of Runnable interface with different implementation using lambda expressions.
// Use them with threads.
public class Task1 {

    public static void execute() {
        System.out.println("Task 1 ==> several runnable instances with implementations");
        IntStream
                .generate(new AtomicInteger()::getAndIncrement)
                .limit(5)
                .forEach(i -> {
                    Thread thread = new Thread(() ->
                            System.out.println("Runnable instance: " + i));
                    thread.start();
                });
    }

}
