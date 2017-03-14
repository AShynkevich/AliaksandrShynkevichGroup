package com.epam.lab.mentoring.webservice;

import com.epam.lab.mentoring.webservice.soap.FileSharingWeb;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;

public class JettyServlet extends CXFNonSpringServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(JettyServlet.class);

    @Override
    protected void loadBus(ServletConfig sc) {
        super.loadBus(sc);
        configureCustomBus();
    }

    private void configureCustomBus() {
        LOGGER.info("Custom bus instantiation.");
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);

        Endpoint.publish(FileSharingWeb.SERVICE_ENDPOINT, new FileSharingWeb());
    }
}
