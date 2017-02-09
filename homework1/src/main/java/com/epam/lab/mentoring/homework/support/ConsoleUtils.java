package com.epam.lab.mentoring.homework.support;

import org.apache.commons.lang3.StringUtils;

public final class ConsoleUtils {

    private ConsoleUtils() {
        throw new UnsupportedOperationException();
    }

    public static void clearConsole() {
        // clear screen followed by home
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static boolean validateInput(String input) {
        if (StringUtils.isBlank(input)) {
            System.out.println("Incorrect input!");
            return false;
        }
        return true;
    }
}
