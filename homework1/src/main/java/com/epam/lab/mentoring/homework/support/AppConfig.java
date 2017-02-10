package com.epam.lab.mentoring.homework.support;

import com.epam.lab.mentoring.homework.support.properties.FilePropertyLoader;
import com.epam.lab.mentoring.homework.support.properties.LoadStatus;
import com.epam.lab.mentoring.homework.support.properties.SystemPropertyLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public enum AppConfig {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    private Properties properties;

    {
        LoadStatus fileLoad = new FilePropertyLoader().load();
        LoadStatus consoleLoad = new SystemPropertyLoader().load();

        if (!fileLoad.isSuccess() && !consoleLoad.isSuccess()) {
            throw new IllegalStateException("Failed to initialize application! No properties found!");
        }

        if (fileLoad.isSuccess()) {
            // using files log
            properties = fileLoad.getResult();
        } else {
            // both cannot be false at that stage
            properties = consoleLoad.getResult();
        }
    }

    public String getProperty(String key) {
        String toReturn = properties.getProperty(key);
        if (StringUtils.isBlank(toReturn)) {
            LOGGER.error("Property [{}] not found.", key);
            throw new IllegalStateException("No property [".concat(key).concat("] specified!"));
        }

        return toReturn;
    }
}
