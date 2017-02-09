package com.epam.lab.mentoring.homework.support.properties;

import java.util.Properties;

import static com.epam.lab.mentoring.homework.support.Constants.REPOSITORY_TYPE_PARAMETER;
import static com.epam.lab.mentoring.homework.support.Constants.SERVICE_TYPE_PARAMETER;
import static com.epam.lab.mentoring.homework.support.Constants.TASK_FILE_KEY;

public class SystemPropertyLoader implements IPropertyLoader {

    @Override
    public LoadStatus load() {
        Properties properties = new Properties();
        // TODO: add some parameter support if needed though I suggest using file

        // TODO: no idea how to make escape DRY there
        String serviceType = System.getProperty(SERVICE_TYPE_PARAMETER);
        if (null == serviceType) {
            return new LoadStatus(false);
        }

        String repositoryType = System.getProperty(REPOSITORY_TYPE_PARAMETER);
        if (null == repositoryType) {
            return new LoadStatus(false);
        }

        String repositoryFile = System.getProperty(TASK_FILE_KEY);
        if (null == repositoryFile) {
            return new LoadStatus(false);
        }

        properties.setProperty(SERVICE_TYPE_PARAMETER, serviceType);
        properties.setProperty(REPOSITORY_TYPE_PARAMETER, repositoryType);
        properties.setProperty(TASK_FILE_KEY, repositoryFile);
        return new LoadStatus(true, properties);
    }
}
