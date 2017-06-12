package com.epam.lab.mentoring;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {
    public void calculate() {
        for (int i = 0; i < 15; i++) {
            System.out.println("Factorial " + i + " => " + factorial(i));
        }
    }

    private BigInteger factorial(int n) {
        return ForkJoinPool.commonPool().invoke(new FactorialTask(BigInteger.ONE, BigInteger.valueOf(n)));
    }

    static class FactorialTask extends RecursiveTask<BigInteger> {
        private BigInteger left, right;

        public FactorialTask(BigInteger left, BigInteger right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected BigInteger compute() {
            if (right.equals(BigInteger.ZERO)) {
                return BigInteger.ONE;
            }

            if (right.subtract(left).equals(BigInteger.ONE)) {
                return right.multiply(left);
            }

            if (right.subtract(left).compareTo(BigInteger.ONE) <= 0) {
                return right;
            }

            BigInteger diff = right.subtract(left).divide(BigInteger.valueOf(2));
            FactorialTask task1 = new FactorialTask(left, left.add(diff));
            task1.fork();

            FactorialTask task2;
            if ((isOdd(right) && isOdd(left)) || (!isOdd(right) && !isOdd(left))) {
                task2 = new FactorialTask(right.subtract(diff).add(BigInteger.ONE), right);
            } else if (isOdd(right) && isOdd(left)) {
                task2 = new FactorialTask(right.subtract(diff).subtract(BigInteger.ONE), right);
            } else {
                task2 = new FactorialTask(right.subtract(diff), right);
            }

            return task2.compute().multiply(task1.join());
        }

        private boolean isOdd(BigInteger i) {
            return !i.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO);
        }
    }
}
