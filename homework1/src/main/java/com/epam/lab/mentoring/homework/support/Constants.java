package com.epam.lab.mentoring.homework.support;

public final class Constants {

    public static final String QUIT_COMMAND = "quit";

    // constants for AppConfig
    public static final String PROPERTY_FILE_KEY = "propertyFile";
    public static final String DEFAULT_PROPERTY_FILE = "app.properties";

    public static final String TASK_FILE_KEY = "taskFile";
    
    public static final String NO_SERVICE_FOUND_EXCEPTION = "No service of type: %s found!";
    public static final String NO_REPOSITORY_FOUND_EXCEPTION = "No repository for resource type: %s found!";

    public static final String CONSOLE_TEMPLATE = "Available actions -->\n" +
            "Add task: 1\n" +
            "Update task: 2\n" +
            "Find task: 3\n" +
            "Remove task: 4\n" +
            "Show all tasks: 5\n";

    public static final String SERVICE_TYPE_PARAMETER = "service";
    public static final String REPOSITORY_TYPE_PARAMETER = "repository";
}
