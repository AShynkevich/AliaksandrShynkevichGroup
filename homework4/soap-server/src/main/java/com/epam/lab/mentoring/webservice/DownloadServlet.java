package com.epam.lab.mentoring.webservice;

import com.epam.lab.mentoring.webservice.resource.FileSharingSupport;
import com.epam.lab.mentoring.webservice.soap.FileSharingWeb;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Attempt to process get request [{}].", req.getRequestURI());
        String filepath = getFilePath(req);

        LOGGER.info("Attempt to locate file: [{}].", filepath);
        resp.setHeader("Content-disposition", "attachment; filename=" + FilenameUtils.getName(filepath));
        writeFileToStream(resp.getOutputStream(), filepath);
    }

    private void writeFileToStream(OutputStream os, String filepath) throws IOException {
        byte[] file = FileSharingSupport.readFile(filepath);
        os.write(file);
        os.flush();
    }

    private String getFilePath(HttpServletRequest req) {
        LOGGER.info("Attempt to get file path from request.");
        String requestUri = req.getRequestURI();
        return requestUri.substring(FileSharingWeb.SERVER_PATH.length(), requestUri.length());
    }
}
