package com.epam.lab.mentoring.webservice.soap;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface IFileSharingWebService {
    List<String> listFiles();

    void createFile(String filename, byte[] fileBytes);

    void deleteFile(String filename);

    byte[] readFile(String filename);
}
