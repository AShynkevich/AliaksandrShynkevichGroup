package com.epam.lab.mentoring;

import com.epam.lab.mentoring.classloader.JarResourceLoader;
import com.epam.lab.mentoring.console.ConsoleInputController;
import com.epam.lab.mentoring.watcher.WatcherThread;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.epam.lab.mentoring.ApplicationConstants.*;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        WatcherThread thread = new WatcherThread(EXTENSION_FOLDER);
        thread.start();

        firstRunClassLoad();
        runConsoleLoop();

        thread.stopThread();
    }

    private static void runConsoleLoop() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = null;
            ConsoleInputController controller = new ConsoleInputController(br);
            while (!QUIT_COMMAND.equals(StringUtils.trim(input))) {
                System.out.println(CONSOLE_TEMPLATE);
                System.out.println("input (to exit write [quit]):> ");

                input = br.readLine();
                controller.handleInput(input);
            }
        } catch (IOException exc) {
            LOGGER.error("Failed to process input.", exc);
        }
    }

    private static void firstRunClassLoad() throws IOException {
        Files.walk(Paths.get(EXTENSION_FOLDER))
                .filter(p -> p.toString().endsWith(PLUGIN_EXTENSION_WITH_POINT))
                .forEach(JarResourceLoader::load);
    }
}
