package com.epam.lab.mentoring.homework.support;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileRepositoryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositoryUtils.class);

    private FileRepositoryUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> List<T> readListFromFile(String file) {
        File fileInstance = checkFileAndCreate(file); // not needed maybe
        try (FileInputStream fis = new FileInputStream(fileInstance)) {
            List<T> tasks = SerializationUtils.deserialize(fis);
            if (CollectionUtils.isNotEmpty(tasks)) {
                return tasks;
            }
        }catch (SerializationException e) {
            LOGGER.info("Nothing to deserialize! File appears to be empty");
        } catch (IOException e) {
            LOGGER.error("Failed to read repository file.", e);
        }
        return new ArrayList<T>();
    }

    public static <T>  void writeListToFile(List<T> list, String file) {
        File fileInstance = checkFileAndCreate(file);
        try (FileOutputStream fos = new FileOutputStream(fileInstance)) {
            SerializationUtils.serialize((Serializable) list, fos);
        } catch (IOException e) {
            LOGGER.error("Failed to write repository file.", e);
        }
    }

    private static File checkFileAndCreate(String filename) {
        try {
            LOGGER.debug("Attempt to check file.");
            File file = new File(filename);
            if (!file.exists()) {
                LOGGER.info("File not found! Attempt to create file [{}].", file.getAbsolutePath());
                if (null != file.getParentFile()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            LOGGER.error("Failed to create file [{}]", filename, e);
            throw new IllegalStateException("Failed to create repository file!");
        }
    }
}
