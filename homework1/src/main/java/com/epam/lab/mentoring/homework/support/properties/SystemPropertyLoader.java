package com.epam.lab.mentoring.homework.support.properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static com.epam.lab.mentoring.homework.support.Constants.DEFAULT_FILE_NAME;
import static com.epam.lab.mentoring.homework.support.Constants.TASK_FILE_KEY;

public class SystemPropertyLoader implements IPropertyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemPropertyLoader.class);

    @Override
    public Properties load() {
        LOGGER.info("Attempt to load system properties.");
        Properties properties = new Properties();

        String repositoryFile = System.getProperty(TASK_FILE_KEY);
        properties.setProperty(TASK_FILE_KEY, StringUtils.defaultIfBlank(repositoryFile, DEFAULT_FILE_NAME));
        return properties;
    }
}
