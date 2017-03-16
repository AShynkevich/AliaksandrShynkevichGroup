package com.epam.lab.mentoring.webservice.resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class FileSharingSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSharingSupport.class);
    private static final String SHARED_PATH = "./shared-repository";

    static {
        try {
            Files.createDirectories(Paths.get(SHARED_PATH));
        } catch (IOException e) {
            LOGGER.error("Failed to create directory: [{}].", SHARED_PATH, e);
        }
    }

    public static List<String> listFiles() {
        List<String> files = new ArrayList<>();
        try {
            Files.walk(Paths.get(SHARED_PATH))
                    .filter(Files::isRegularFile)
                    .forEach(path -> { files.add(path.toFile().getPath()); });
        } catch (IOException e) {
            LOGGER.error("Failed to process repository: [{}].", SHARED_PATH, e);
        }
        return files;
    }

    public static void createFile(String filename, byte[] fileBytes) {
        File file = new File(makePath(filename));
        file.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            file.createNewFile();
            if (null == fileBytes) {
                LOGGER.info("Created empty file: [{}].", filename);
            } else {
                fos.write(fileBytes);
            }
        } catch (IOException exc) {
            LOGGER.error("Failed to write to file [{}].", filename, exc);
        }
    }

    public static void deleteFile(String filename) {
        File file = new File(makePath(filename));

        if (file.exists()) {
            if (!file.delete()) {
                LOGGER.error("Failed to delete file: [{}].", filename);
            }
        } else {
            LOGGER.info("File [{}] does not exist!", filename);
        }
    }

    public static byte[] readFile(String filename) {
        try (FileInputStream fis = new FileInputStream(makePath(filename))) {
            return IOUtils.toByteArray(fis);
        } catch (IOException exc) {
            LOGGER.error("Failed to read file: [{}].", exc);
        }

        return new byte[0];
    }

    private static String makePath(String filename) {
        return SHARED_PATH + File.separator + filename;
    }

}
