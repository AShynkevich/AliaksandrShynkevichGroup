package com.epam.lab.mentoring.homework.support;

public final class ConsoleUtils {

    private ConsoleUtils() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static void clearConsole() {
        // clear screen followed by home
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
