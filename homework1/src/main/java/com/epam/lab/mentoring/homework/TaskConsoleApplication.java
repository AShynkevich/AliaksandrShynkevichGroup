package com.epam.lab.mentoring.homework;

import com.epam.lab.mentoring.homework.console.*;
import com.epam.lab.mentoring.homework.service.ITaskService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static com.epam.lab.mentoring.homework.support.Constants.*;
import static com.epam.lab.mentoring.homework.support.Constants.SHOW_ALL_TASKS_CMD_ID;

public class TaskConsoleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsoleApplication.class);

    private Map<String, IConsoleInputHandler> consoleHandlers;
    private ITaskService taskService;

    public TaskConsoleApplication() {
        prepareConsoleHandlers();
        prepareService();
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = null;
            while (!QUIT_COMMAND.equals(StringUtils.trim(input))) {
                // ConsoleUtils.clearConsole(); // should look fine in console but I did not test
                // in GRADLE looks so so but it is common issue
                System.out.println(CONSOLE_TEMPLATE);
                System.out.print("input:> ");
                input = br.readLine();
                processInput(input, br, taskService);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to process input.", e);
        }
    }

    private void prepareConsoleHandlers() {
        LOGGER.debug("Instantiating console handlers...");
        consoleHandlers = new HashedMap<>();
        consoleHandlers.put(ADD_TASK_CMD_ID, new AddTaskConsoleInputHandler());
        consoleHandlers.put(UPDATE_TASK_CMD_ID, new UpdateTaskConsoleInputHandler());
        consoleHandlers.put(FIND_TASK_CMD_ID, new FindTaskConsoleInputHandler());
        consoleHandlers.put(REMOVE_TASK_CMD_ID, new RemoveTaskConsoleInputHandler());
        consoleHandlers.put(SHOW_ALL_TASKS_CMD_ID, new FindAllTasksConsoleInputHandler());
        LOGGER.debug("Done!");
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

    private void processInput(String inputLine, BufferedReader br, ITaskService service) throws IOException {
        LOGGER.debug("Input command: [{}].\n", inputLine);
        // check is blank
        if (StringUtils.isBlank(inputLine)) {
            System.out.println("No command provided!");
            return;
        }

        if (QUIT_COMMAND.equals(StringUtils.trim(inputLine))) {
            return;
        }

        IConsoleInputHandler handler = consoleHandlers.get(inputLine);
        handler.handleInput(br, service);
    }
}
