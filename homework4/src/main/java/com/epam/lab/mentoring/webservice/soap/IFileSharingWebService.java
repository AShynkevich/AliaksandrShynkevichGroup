package com.epam.lab.mentoring.webservice.soap;

import javax.jws.WebService;

@WebService
public interface IFileSharingWebService {
    String listResources();
}
