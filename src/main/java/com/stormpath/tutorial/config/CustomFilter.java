package com.stormpath.tutorial.config;

import com.stormpath.sdk.servlet.filter.HttpFilter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by tomasz.lelek on 18/12/15.
 */
public class CustomFilter extends HttpFilter {
    private ServletContext context;

    private static final Logger logger = LoggerFactory.getLogger(CustomFilter.class);    
    
    @Override
    protected void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("filter request :" + request.getContextPath());
        logger.info("filter response :" + response.getStatus());
        chain.doFilter(request, response);
    }

    public void destroy() {
        //we can close resources here
    }
}
