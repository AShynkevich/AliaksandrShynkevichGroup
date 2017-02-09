package com.epam.lab.mentoring;

import com.epam.lab.mentoring.homework.LoggerProvider;
import com.epam.lab.mentoring.homework.TaskRepositoryFactory;
import com.epam.lab.mentoring.homework.TaskServiceFactory;
import com.epam.lab.mentoring.homework.service.ITaskService;
import com.epam.lab.mentoring.homework.support.AppConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.*;

import static com.epam.lab.mentoring.homework.support.Constants.*;

public class Main {

    private static final Logger LOGGER = LoggerProvider.getLogger();

    private static ITaskService createService() {
        ITaskService taskService = null;
        try {
            taskService = TaskServiceFactory
                    .getTaskService(AppConfig.INSTANCE.getProperty(SERVICE_TYPE_PARAMETER))
                    .withRepository(TaskRepositoryFactory
                            .getTaskRepository(AppConfig.INSTANCE.getProperty(REPOSITORY_TYPE_PARAMETER)));
        } catch (IllegalStateException e) {
            LOGGER.error("Failed to create service.", e);
            System.exit(1);
        }
        return taskService;
    }

    private static void handleConsoleInput(ITaskService service) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = null;
            while (!QUIT_COMMAND.equals(StringUtils.trim(input))) {
                // in GRADLE looks so so but it is common issue
                System.out.println(CONSOLE_TEMPLATE);
                System.out.print("input:> ");
                processInput(br.readLine());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to process input.", e);
        }
    }

    private static void processInput(String inputLine) {
        LOGGER.info("Input command: [{}].\n", inputLine);
    }

    public static void main(String[] args) {
        handleConsoleInput(createService());
    }

}
