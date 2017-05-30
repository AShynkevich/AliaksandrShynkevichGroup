package com.epam.lab.mentoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println("====task 2===");
        task2();
        System.out.println("====task 3===");
        task3();
    }

    // Use forEach method for printing information about all the persons.
    // Use the method reference.
    private static void task3() {
        List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Pjotr", 19),
                new Person("Michelle", 21)
        );
        System.out.println("List of people");
        people.forEach(Main::printObject);
    }

    private static <T> void printObject(T object) {
        System.out.println(object.toString());
    }

    // Create: a Person class with name and age fields;
    // an array of Persons; two instances of Comparator interface using lambda expressions: first one for comparing by name,
    // second one – by age; Then sort them using these comparators;
    private static void task2() {
        List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Pjotr", 19),
                new Person("Michelle", 21)
        );

        Comparator<Person> nameComparator = (Person o1, Person o2) -> {
            String name1 = o1.getName();
            String name2 = o2.getName();

            if (name1 == name2) { return 0; }
            if (null == name1) { return -1; }
            if (null == name2) { return 1; }
            return name1.toLowerCase().compareTo(name2.toLowerCase());
        };

        Comparator<Person> ageComparator = (Person o1, Person o2) -> o1.getAge() - o2.getAge();

        List<Person> list1 = new ArrayList<>(people);
        List<Person> list2 = new ArrayList<>(people);

        list1 = list1.stream()
                .sorted(nameComparator)
                .collect(Collectors.toList());
        list2 = list2.stream()
                .sorted(ageComparator)
                .collect(Collectors.toList());
        System.out.println("Sorted by name ->");
        list1.forEach(Main::printObject);
        System.out.println("Sorted by age ->");
        list2.forEach(Main::printObject);
    }
}
