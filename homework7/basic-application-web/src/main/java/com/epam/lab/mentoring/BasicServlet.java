package com.epam.lab.mentoring;

import com.epam.lab.mentoring.api.IBasicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

    public class BasicServlet extends HttpServlet {

        private IBasicService basicService;

        @Override
        public void init() throws ServletException {
            basicService = new BasicService();
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setAttribute("message", basicService.provideMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        }
    }
