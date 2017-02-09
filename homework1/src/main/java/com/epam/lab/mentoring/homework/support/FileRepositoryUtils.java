package com.epam.lab.mentoring.homework.support;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileRepositoryUtils {

    private FileRepositoryUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> List<T> readListFromFile(String file) {
        checkFileAndCreate(file); // not needed maybe
        try (FileInputStream fis = new FileInputStream(file)) {
            List<T> tasks = SerializationUtils.deserialize(fis);
            if (CollectionUtils.isNotEmpty(tasks)) {
                return tasks;
            }
        } catch (IOException e) {
            // TODO: add logger
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    public static <T>  void writeListToFile(List<T> list, String file) {
        checkFileAndCreate(file);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            SerializationUtils.serialize((Serializable) list, fos);
        } catch (IOException e) {
            // TODO: add logger
            e.printStackTrace();
        }
    }

    private static void checkFileAndCreate(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.getParentFile().createNewFile();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();

            throw new IllegalStateException("Failed to create repository file!");
        }
    }
}
