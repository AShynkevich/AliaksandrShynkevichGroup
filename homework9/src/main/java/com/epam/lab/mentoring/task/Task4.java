package com.epam.lab.mentoring.task;

import com.epam.lab.mentoring.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Implement each of main Java Standard Library functional interfaces (supplier, predicate etc.)
// using lambda expressions
public class Task4 {
    public static void execute() {
        System.out.println("Task 4 ==>  implement main JSL functional interfaces");
        // Supplier<T>

        List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Pjotr", 19),
                new Person("Michelle", 21)
        );

        System.out.println("Initial people set:" + people);
        System.out.println("Filtered people by (age >= 20) using predicate.");
        System.out.println(people.stream()
                .filter(p -> p.getAge() >= 20)
                .collect(Collectors.toList()));

        System.out.println("Changed people by applying function (age - 5).");
        System.out.println(people.stream()
                .map(p -> new Person(p.getName(), p.getAge() - 5))
                .collect(Collectors.toList()));

        System.out.println("Print adult (age >= 21) people only in consumer.");
        people.forEach(p -> {
            if (p.getAge() >= 21) {
                System.out.println(p.getName() + " is adult");
            };
        });

        List<String> names = Arrays.asList("John", "Pjotr", "Michelle");
        System.out.println("Create adult people using supplier.");
        List<Person> peopleGen = Stream.generate(() -> new Person(names.get(new Random().nextInt(3)), 21))
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(peopleGen);
    }
}
