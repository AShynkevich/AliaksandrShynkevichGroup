package com.epam.lab.mentoring;

import com.epam.lab.mentoring.webservice.soap.FileSharingWebService;
import com.epam.lab.mentoring.webservice.soap.IFileSharingWebService;

public class SoapClient {

    public static void main(String[] args) {
        FileSharingWebService service = new FileSharingWebService();
        IFileSharingWebService port = service.getFileSharingWebServicePort();

        System.out.println("Well SOAP response: " + port.listResources());
    }

}
