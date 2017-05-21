package com.epam.lab.mentoring.repository.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSystemUtils {
    private static final Logger log = LoggerFactory.getLogger(FileSystemUtils.class);

    public static boolean isFileExist(String directory, String filename) {
        return new File(makePath(directory, filename)).exists();
    }

    public static List<String> listFiles(String directory) {
        File folder = new File(directory);
        List<String> files = new ArrayList<>();
        try {
            Files.walk(Paths.get(folder.getCanonicalPath()))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            files.add(Paths.get(folder.getCanonicalPath()).relativize(path).toString());
                        } catch (IOException e) {
                            log.error("Failed to iterate [{}].", path, e);
                        }
                    });
        } catch (NoSuchFileException e) {
            log.warn("No repository [{}] exist! Creating new one.", directory);
            File newRepo = new File(directory);
            newRepo.mkdir();
            log.info("Repository created [{}].", newRepo.getAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to iterate [{}].", directory);
        }
        return files;
    }

    public static void createFile(String directory, String filename, byte[] fileBytes) {
        File file = new File(makePath(directory, filename));
        file.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            file.createNewFile();
            if (null == fileBytes) {
                log.warn("File [{}] is empty.", filename);
            }
            else {
                fos.write(fileBytes);
            }
        } catch (IOException exc) {
            log.error("Failed to create new file [{}] in [{}].", filename, directory, exc);
        }
    }

    private static String makePath(String folder, String filename) {
        return folder + File.separator + filename;
    }
}
