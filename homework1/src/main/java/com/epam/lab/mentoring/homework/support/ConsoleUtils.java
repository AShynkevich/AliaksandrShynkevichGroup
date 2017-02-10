package com.epam.lab.mentoring.homework.support;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;


public final class ConsoleUtils {

    private ConsoleUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean validateInput(String input) {
        if (StringUtils.isBlank(input)) {
            System.out.println("Incorrect input!");
            return false;
        }
        return true;
    }

    public static LocalDateTime parseDate(String input) {
        try {
            return LocalDateTime.parse(input);
        } catch (IllegalArgumentException exc) {
            System.out.println("Incorrect date format!");
        }
        return null;
    }
}
