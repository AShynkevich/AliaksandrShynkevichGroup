package com.epam.lab.mentoring.repository.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSystemUtils {

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
                            e.printStackTrace();
                        }
                    });
        } catch (NoSuchFileException e) {
            new File(directory).mkdir();
            throw new IllegalStateException(new File(directory).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static void createFile(String directory, String filename, byte[] fileBytes) {
        File file = new File(makePath(directory, filename));
        file.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            file.createNewFile();
            if (null == fileBytes) {
                throw new IllegalStateException();
            }
            else {
                fos.write(fileBytes);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private static String makePath(String folder, String filename) {
        return folder + File.separator + filename;
    }

}
