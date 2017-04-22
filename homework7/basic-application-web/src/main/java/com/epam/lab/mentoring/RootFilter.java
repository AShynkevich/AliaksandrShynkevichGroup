package com.epam.lab.mentoring;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RootFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getRequestURI().equals("/basic%2Dapplication%2Dweb/")) {
            req.getRequestDispatcher("/WEB-INF/index.html").forward(req, resp);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
