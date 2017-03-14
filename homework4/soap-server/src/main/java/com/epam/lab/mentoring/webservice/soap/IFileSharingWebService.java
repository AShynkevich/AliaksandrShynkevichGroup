package com.epam.lab.mentoring.webservice.soap;

import javax.jws.WebService;

@WebService
public interface IFileSharingWebService {
    String listFiles();

    void createFile(String filename, byte[] fileBytes);

    void deleteFile(String filename);

    byte[] readFile(String filename);
}
