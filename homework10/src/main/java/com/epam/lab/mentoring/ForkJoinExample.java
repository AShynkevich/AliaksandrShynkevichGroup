package com.epam.lab.mentoring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinExample extends AbstractExample {
    @Override
    public void calculate() {
        List<CalculationTask> tasks = new ArrayList<>();
        IntStream.range(0, getInputSize()).forEach(idx -> {
            tasks.add(new CalculationTask(idx, this));
        });

        ForkJoinTask.invokeAll(tasks);
        System.out.println("Calculation result => " + output);
    }

    public static class CalculationTask extends RecursiveTask {
        private int indexToWorkWith;
        private AbstractExample actor;

        public CalculationTask(int indexToWorkWith, AbstractExample actor) {
            this.indexToWorkWith = indexToWorkWith;
            this.actor = actor;
        }

        @Override
        protected Object compute() {
            actor.calculate(indexToWorkWith);
            return null;
        }
    }
}
