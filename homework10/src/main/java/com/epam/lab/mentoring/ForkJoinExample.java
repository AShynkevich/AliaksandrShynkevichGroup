package com.epam.lab.mentoring;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinExample extends AbstractExample {
    @Override
    public void calculate() {
        ElementSetAction action = new ElementSetAction(this.input, 0, this);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(action);
        pool.shutdown();
        try {
            pool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Calculation result => " + output);
    }

    public static class ElementSetAction extends RecursiveAction {
        private AbstractExample core;
        private int persistentIndex;
        private List<Pair<Integer, Integer>> input;

        public ElementSetAction(List<Pair<Integer, Integer>> input, int persistentIndex, AbstractExample core) {
            this.input = input;
            this.persistentIndex = persistentIndex;
            this.core = core;
        }

        @Override
        protected void compute() {
            if (input.size() == 1) {
                core.calculate(persistentIndex);
            } else {
                List<ElementSetAction> actions = new ArrayList<>();
                int leftStartIndex = input.size() / 2;

                List<Pair<Integer, Integer>> subList1 =
                        input.subList(0, leftStartIndex);
                ElementSetAction action1 = new ElementSetAction(subList1, persistentIndex, core);
                action1.fork();

                List<Pair<Integer, Integer>> subList2 =
                        input.subList(leftStartIndex, input.size());
                ElementSetAction action2 = new ElementSetAction(subList2, persistentIndex + leftStartIndex, core);
                action2.fork();

                actions.add(action1);
                actions.add(action2);

                for (RecursiveAction action: actions) {
                    action.join();
                }
            }
        }
    }
}
