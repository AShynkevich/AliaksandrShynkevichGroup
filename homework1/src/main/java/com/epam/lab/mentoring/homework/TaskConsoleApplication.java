package com.epam.lab.mentoring.homework;

import com.epam.lab.mentoring.homework.console.*;
import com.epam.lab.mentoring.homework.service.ITaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.epam.lab.mentoring.homework.support.Constants.*;

public class TaskConsoleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsoleApplication.class);

    private ITaskService taskService;

    public TaskConsoleApplication() {
        prepareService();
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = null;
            ConsoleInputController controller = new ConsoleInputController(br, taskService);
            while (!QUIT_COMMAND.equals(StringUtils.trim(input))) {
                // in GRADLE looks so so but it is common issue
                System.out.println(CONSOLE_TEMPLATE);
                System.out.print("input:> ");
                input = br.readLine();

                controller.handleInput(input);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to process input.", e);
        }
    }

    private void prepareService() {
        try {
            taskService = TaskServiceFactory
                    .getTaskService()
                    .withRepository(TaskRepositoryFactory.getTaskRepository(FILE_REPOSITORY));
        } catch (IllegalStateException e) {
            LOGGER.error("Failed to create service.", e);
            System.exit(1);
        }
    }
}
