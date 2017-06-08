package com.epam.lab.mentoring;

import org.apache.commons.lang3.tuple.Pair;

import java.util.stream.IntStream;

public class ClassicModelExample extends AbstractExample {
    @Override
    public void calculate() {
        IntStream.range(0, getInputSize()).forEach(idx -> {
            Pair<Integer, Integer> pair = getByIndex(idx);

            Thread threadWrapper = new Thread(
                    () -> this.calculate(idx)
            );
            threadWrapper.start();
        });

        try {
            Thread.sleep(4000); // waiting for threads to finish execution just in case
            System.out.println("Calculation result => " + output);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
