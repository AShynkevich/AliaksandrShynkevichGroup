package com.epam.lab.mentoring.console;

import org.apache.commons.lang3.StringUtils;

public final class ConsoleUtils {
    public static boolean validateInput(String input) {
        if (StringUtils.isBlank(input)) {
            System.out.println("Incorrect input!");
            return false;
        }
        return true;
    }
}
