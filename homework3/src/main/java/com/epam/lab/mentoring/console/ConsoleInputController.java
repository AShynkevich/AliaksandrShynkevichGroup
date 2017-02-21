package com.epam.lab.mentoring.console;

import com.epam.lab.mentoring.ApplicationConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.lab.mentoring.ApplicationConstants.LOAD_CLASSES_CMD;

public class ConsoleInputController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleInputController.class);

    private BufferedReader reader;
    private Map<String, IConsoleInputHandler> consoleHanders;

    public ConsoleInputController(BufferedReader reader) {
        this.reader = reader;
    }

    public void handleInput(String inputLine) throws IOException {
        if (StringUtils.isBlank(inputLine)) {
            System.out.println("No command provided!");
            return;
        }

        if (ApplicationConstants.QUIT_COMMAND.equals(StringUtils.trim(inputLine))) {
            return;
        }

        IConsoleInputHandler handler = consoleHanders.get(inputLine);
        if (null == handler) {
            LOGGER.debug("Unsupported handler for action: [{}].", inputLine);
            return;
        }

        handler.handleInput();
    }

    private void prepareConsoleHandlers() {
        LOGGER.debug("Instantiating console handlers...");
        consoleHanders = new HashMap<>();
        consoleHanders.put(LOAD_CLASSES_CMD, new LoadClassesConsoleInputHandler(reader));
        LOGGER.debug("Done!");
    }
}
