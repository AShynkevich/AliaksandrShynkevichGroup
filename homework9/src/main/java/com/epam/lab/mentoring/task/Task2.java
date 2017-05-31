package com.epam.lab.mentoring.task;

import com.epam.lab.mentoring.Main;
import com.epam.lab.mentoring.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Create: a Person class with name and age fields;
// an array of Persons; two instances of Comparator interface using lambda expressions: first one for comparing by name,
// second one – by age; Then sort them using these comparators;
public class Task2 {

    public static void execute() {
        System.out.println("Task 2 ==> ");
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

        System.out.println("Sorted by name ==>");
        list1.forEach(TaskSupport::printObject);
        System.out.println("Sorted by age ==>");
        list2.forEach(TaskSupport::printObject);
    }

}
