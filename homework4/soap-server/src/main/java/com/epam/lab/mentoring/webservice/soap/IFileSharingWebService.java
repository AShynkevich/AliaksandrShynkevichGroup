package com.epam.lab.mentoring.webservice.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IFileSharingWebService {
    @WebMethod
    List<String> listFiles();

    @WebMethod
    void createFile(@WebParam(name = "fileName") String filename, @WebParam(name = "bytes") byte[] fileBytes);

    @WebMethod
    void deleteFile(@WebParam(name = "fileName") String filename);

    @WebMethod
    String readFile(@WebParam(name="fileName") String filename);
}
