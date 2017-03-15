package com.epam.lab.mentoring.webservice.soap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.epam.lab.mentoring.webservice.soap.IFileSharingWebService")
public class FileSharingWeb implements IFileSharingWebService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSharingWeb.class);
    private static final String SHARED_PATH = "./shared-repository";
    public static final String SERVICE_ENDPOINT = "/fileSharingService";

    static {
        try {
            Files.createDirectories(Paths.get(SHARED_PATH));
        } catch (IOException e) {
            LOGGER.error("Failed to create directory: [{}].", SHARED_PATH, e);
        }
    }

    @Override
    public List<String> listFiles() {
        LOGGER.info("Attempt to list files");
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

    @Override
    public void createFile(String filename, byte[] fileBytes) {
        LOGGER.info("Attempt to create file [{}].", filename);

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

    @Override
    public void deleteFile(String filename) {
        LOGGER.info("Attempt to delete file [{}].", filename);
        File file = new File(makePath(filename));

        if (file.exists()) {
            if (!file.delete()) {
                LOGGER.error("Failed to delete file: [{}].", filename);
            }
        } else {
            LOGGER.info("File [{}] does not exist!", filename);
        }
    }

    @Override
    public byte[] readFile(String filename) {
        LOGGER.info("Attempt to read file [{}].", filename);

        try (FileInputStream fis = new FileInputStream(makePath(filename))) {
            return IOUtils.toByteArray(fis);
        } catch (IOException exc) {
            LOGGER.error("Failed to read file: [{}].", exc);
        }

        return new byte[0];
    }

    private String makePath(String filename) {
        return SHARED_PATH + File.separator + filename;
    }
}
