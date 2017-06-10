package com.epam.lab.mentoring;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class UtilConcurrentExample extends AbstractExample {
    @Override
    public void calculate() {
        ExecutorService executorService = Executors.newFixedThreadPool(getInputSize());
        IntStream.range(0, getInputSize()).forEach(idx -> {
            executorService.submit(() -> this.calculate(idx));
        });
        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Calculation result => " + output);
    }
}
