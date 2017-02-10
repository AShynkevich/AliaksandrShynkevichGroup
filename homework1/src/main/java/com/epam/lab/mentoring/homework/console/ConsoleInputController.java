package com.epam.lab.mentoring.homework.console;

import com.epam.lab.mentoring.homework.service.ITaskService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static com.epam.lab.mentoring.homework.support.Constants.*;
import static com.epam.lab.mentoring.homework.support.Constants.SHOW_ALL_TASKS_CMD_ID;

public class ConsoleInputController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleInputController.class);

    private BufferedReader reader;
    private ITaskService taskService;
    private Map<String, IConsoleInputHandler> consoleHandlers;

    public ConsoleInputController(BufferedReader reader, ITaskService taskService) {
        this.reader = reader;
        this.taskService = taskService;

        prepareConsoleHandlers();
    }

    public void handleInput(String inputLine) throws IOException {
        if (StringUtils.isBlank(inputLine)) {
            System.out.println("No command provided!");
            return;
        }

        if (QUIT_COMMAND.equals(StringUtils.trim(inputLine))) {
            return;
        }

        IConsoleInputHandler handler = consoleHandlers.get(inputLine);
        if (null == handler) {
            LOGGER.debug("Unsupported handler for action: [{}].", inputLine);
            return;
        }

        handler.handleInput();
    }

    private void prepareConsoleHandlers() {
        LOGGER.debug("Instantiating console handlers...");
        consoleHandlers = new HashedMap<>();
        consoleHandlers.put(ADD_TASK_CMD_ID, new AddTaskConsoleInputHandler(reader, taskService));
        consoleHandlers.put(UPDATE_TASK_CMD_ID, new UpdateTaskConsoleInputHandler(reader, taskService));
        consoleHandlers.put(FIND_TASK_CMD_ID, new FindTaskConsoleInputHandler(reader, taskService));
        consoleHandlers.put(REMOVE_TASK_CMD_ID, new RemoveTaskConsoleInputHandler(reader, taskService));
        consoleHandlers.put(SHOW_ALL_TASKS_CMD_ID, new FindAllTasksConsoleInputHandler(reader, taskService));
        LOGGER.debug("Done!");
    }
}
