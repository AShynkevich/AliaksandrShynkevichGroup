package com.epam.lab.mentoring.homework.support.properties;

import com.epam.lab.mentoring.homework.support.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static com.epam.lab.mentoring.homework.support.Constants.DEFAULT_PROPERTY_FILE;
import static com.epam.lab.mentoring.homework.support.Constants.PROPERTY_FILE_KEY;

public class FilePropertyLoader implements IPropertyLoader {

    @Override
    public LoadStatus load() {
        String customPropertyFile = System.getProperty(PROPERTY_FILE_KEY);
        URL defaultPropertyFile = Constants.class.getClassLoader().getResource(DEFAULT_PROPERTY_FILE);

        File input;
        if (StringUtils.isBlank(customPropertyFile)) {
            if (null == defaultPropertyFile || !new File(defaultPropertyFile.getFile()).exists()) {
                return new LoadStatus(false);
            } else {
                input = new File(defaultPropertyFile.getFile());
            }
        } else {
            input = new File(customPropertyFile);
        }

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(input)) {
            properties.load(fis);
        } catch (IOException e) {
            // TODO: add logger
            e.printStackTrace();
        }

        return new LoadStatus(true, properties);
    }
}
