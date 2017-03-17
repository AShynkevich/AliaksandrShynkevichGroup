package com.epam.lab.mentoring.webservice.resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public enum FileSharingSupport {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSharingSupport.class);
    private static final String SHARED_PATH = "shared-repository";
    private static final File SHARED_FOLDER = new File(SHARED_PATH);

    public List<String> listFiles() {
        List<String> files = new ArrayList<>();
        try {
            Files.walk(Paths.get(SHARED_FOLDER.getCanonicalPath()))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            files.add(Paths.get(SHARED_FOLDER.getCanonicalPath()).relativize(path).toString());
                        } catch (IOException e) {
                            LOGGER.info("Failed to get canonical path of [{}].", SHARED_FOLDER);
                        }
                    });
        } catch (IOException e) {
            LOGGER.error("Failed to process repository: [{}].", SHARED_PATH, e);
        }
        return files;
    }

    public void createFile(String filename, byte[] fileBytes) {
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

    public void deleteFile(String filename) {
        File file = new File(makePath(filename));

        if (file.exists()) {
            if (!file.delete()) {
                LOGGER.error("Failed to delete file: [{}].", filename);
            }
        } else {
            LOGGER.info("File [{}] does not exist!", filename);
        }
    }

    public byte[] readFile(String filename) {
        try (FileInputStream fis = new FileInputStream(new File(makePath(filename)))) {
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
