package com.epam.lab.mentoring.webservice.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;

@WebService(endpointInterface = "com.epam.lab.mentoring.webservice.soap.IFileSharingWebService")
public class FileSharingWeb implements IFileSharingWebService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSharingWeb.class);
    public static final String SERVICE_ENDPOINT = "/fileSharingService";

    @Override
    public String listFiles() {
        return null;
    }

    @Override
    public void createFile(String filename, byte[] fileBytes) {

    }

    @Override
    public void deleteFile(String filename) {

    }

    @Override
    public byte[] readFile(String filename) {
        return new byte[0];
    }
}
