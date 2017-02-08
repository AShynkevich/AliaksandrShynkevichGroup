package com.epam.lab.mentoring;

import com.epam.lab.mentoring.homework.TaskRepositoryFactory;
import com.epam.lab.mentoring.homework.TaskServiceFactory;
import com.epam.lab.mentoring.homework.service.ITaskService;
import com.epam.lab.mentoring.homework.support.AppConfig;
import com.epam.lab.mentoring.homework.support.ConsoleUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

import static com.epam.lab.mentoring.homework.support.Constants.*;

public class Main {

    private static ITaskService createService() {
        ITaskService taskService = null;
        try {
            taskService = TaskServiceFactory
                    .getTaskService(AppConfig.INSTANCE.getProperty(SERVICE_TYPE_PARAMETER))
                    .withRepository(TaskRepositoryFactory
                            .getTaskRepository(AppConfig.INSTANCE.getProperty(REPOSITORY_TYPE_PARAMETER)));
        } catch (IllegalStateException e) {
            // TODO: create logger and maybe throw some exception
            e.printStackTrace();
            System.exit(1);
        }
        return taskService;
    }

    private static void handleConsoleInput(ITaskService service) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            do {
                ConsoleUtils.clearConsole();
                // in GRADLE looks so so but it is common issue
                System.out.println(CONSOLE_TEMPLATE);
                System.out.print("input:> ");

            } while (!QUIT_COMMAND.equals(StringUtils.trim(input = br.readLine())));
        } catch (IOException e) {
            // TODO: create logger
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        handleConsoleInput(createService());
    }

}
