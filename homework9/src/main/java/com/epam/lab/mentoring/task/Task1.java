package com.epam.lab.mentoring.task;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

// Create several instances of Runnable interface with different implementation using lambda expressions.
// Use them with threads.
public class Task1 {

    public static void execute() {
        System.out.println("Task 1 ==> ");
        IntStream
                .generate(new AtomicInteger()::getAndIncrement)
                .limit(5)
                .forEach(i -> {
                    Thread thread = new Thread(() ->
                            System.out.println("Iteration: " + i));
                    thread.start();
                });
    }

}
