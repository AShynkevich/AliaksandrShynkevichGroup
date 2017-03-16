package com.epam.lab.mentoring.webservice.soap;

import com.epam.lab.mentoring.webservice.resource.FileSharingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.epam.lab.mentoring.webservice.soap.IFileSharingWebService")
public class FileSharingWeb implements IFileSharingWebService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSharingWeb.class);
    public static final String SERVICE_ENDPOINT = "/fileSharingService";
    private static final String HOST = "http://localhost:8080";
    public static final String SERVER_PATH= "/soap-server/download";

    @Override
    public List<String> listFiles() {
        LOGGER.info("Attempt to list files");
        return FileSharingSupport.listFiles();
    }

    @Override
    public void createFile(String filename, byte[] fileBytes) {
        LOGGER.info("Attempt to create file [{}].", filename);
        FileSharingSupport.createFile(filename, fileBytes);
    }

    @Override
    public void deleteFile(String filename) {
        LOGGER.info("Attempt to delete file [{}].", filename);
        FileSharingSupport.deleteFile(filename);
    }

    @Override
    public String readFile(String filename) {
        LOGGER.info("Attempt to provide link to file [{}].", filename);
        if (filename.startsWith("/")) {
            return HOST + SERVER_PATH + filename;
        } else {
            return HOST + SERVER_PATH + "/" + filename;
        }
    }
}
