package com.epam.lab.mentoring;

public class Main {

    public static void main(String[] args) {
        System.out.println("Classic model example.");
        new ClassicModelExample().calculate();

        System.out.println("util.concurrent example.");
        new UtilConcurrentExample().calculate();

        System.out.println("Fork/join example.");
        new ForkJoinExample().calculate();
    }
}
