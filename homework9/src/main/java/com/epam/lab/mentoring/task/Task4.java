package com.epam.lab.mentoring.task;

import com.epam.lab.mentoring.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Implement each of main Java Standard Library functional interfaces (supplier, predicate etc.)
// using lambda expressions
public class Task4 {
    public static void execute() {
        System.out.println("Task 4 ==> ");
        // Supplier<T>

        List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Pjotr", 19),
                new Person("Michelle", 21)
        );

        System.out.println("List of people filtered by predicate.");
        System.out.println(people.stream()
                .filter(p -> p.getAge() >= 20)
                .collect(Collectors.toList()));

        System.out.println("List of people with applied function.");
        System.out.println(people.stream()
                .map(p -> new Person(p.getName(), p.getAge() - 5))
                .collect(Collectors.toList()));

        System.out.println("List of people with adult UAE age");
        people.forEach(p -> {
            if (p.getAge() >= 21) {
                System.out.println(p.getName() + "is adult");
            };
        });

        System.out.println("This is the person supplier.");

    }
}
