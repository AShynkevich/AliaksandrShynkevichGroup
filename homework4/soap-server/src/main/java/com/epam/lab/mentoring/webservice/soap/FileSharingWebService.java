package com.epam.lab.mentoring.webservice.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;

@WebService(endpointInterface = "com.epam.lab.mentoring.webservice.soap.IFileSharingWebService")
public class FileSharingWebService implements IFileSharingWebService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSharingWebService.class);
    public static final String SERVICE_ENDPOINT = "/fileSharingService";

    @Override
    public String listResources() {
        LOGGER.info("Attempt to retrieve list of resources.");
        return "nothing to do here!";
    }
}
