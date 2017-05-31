package com.epam.lab.mentoring.task;

import com.epam.lab.mentoring.Person;

import java.util.Arrays;
import java.util.List;

// Use forEach method for printing information about all the persons.
// Use the method reference.
public class Task3 {

    public static void execute() {
        System.out.println("Task 3 ==> ");
        List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Pjotr", 19),
                new Person("Michelle", 21)
        );
        System.out.println("List of people ==>");
        people.forEach(TaskSupport::printObject);
    }

}
