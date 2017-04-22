package com.epam.lab.mentoring;

import com.epam.lab.mentoring.api.IBasicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServiceServlet extends HttpServlet {

    private IBasicService basicService;

    @Override
    public void init() throws ServletException {
        basicService = new BasicService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream().write(basicService.provideMessage().getBytes());
        resp.getOutputStream().flush();
        resp.getOutputStream().close();
    }
}
