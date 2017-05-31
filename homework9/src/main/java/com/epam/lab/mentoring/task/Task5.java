package com.epam.lab.mentoring.task;

// Create your own functional interface and add several its implementations using
// both lambda expressions and inner anonymous classes.
// Add few default methods to it and use them. Add few static methods to it and use them.
public class Task5 {

    public static void execute() {
        System.out.println("Task 5 ==> ");

        IPrinter anon1 = new IPrinter() {
            @Override
            public void printSomething() {
                System.out.println("I am something anonymous");
            }
        };
        System.out.println("Anonymous example =>");
        anon1.printDefaultSomething();
        anon1.printSomething();

        IPrinter anon2 = () -> System.out.println("I am something lambdaous");
        System.out.println("Lambda example =>");
        anon2.printDefaultSomething();
        anon2.printSomething();

        System.out.println("Static interface method");
        IPrinter.printStatic();
    }

    @FunctionalInterface
    public interface IPrinter {
        void printSomething();

        default void printDefaultSomething() {
            System.out.println("I am default");
        }

        static void printStatic() {
            System.out.println("I am static!");
        }
    }
}
