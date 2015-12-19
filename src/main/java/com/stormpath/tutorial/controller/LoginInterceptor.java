package com.stormpath.tutorial.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;


public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        Enumeration<String> attributeNames = request.getAttributeNames();

        View view = modelAndView.getView();

        logger.info("postHandle Request URL::" + request.getRequestURL().toString()
                + " Sent to Handler :: Current Time=" + System.currentTimeMillis()
                + "r.status : " + response.getStatus() + "req.contextPath : " + request.getContextPath()
                + "handler: " + handler + " MandV" + modelAndView.getModelMap() + "" + modelAndView.getModel()
                        + " att names: " + request.getAttribute("StormpathHttpServletRequest") + " view : " +
                    view);
        
        
        //we can add attributes in the modelAndView and use that in the view page
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception{
        logger.info("afterCompletion Request URL::" + request.getRequestURL().toString()
                + " Sent to Handler :: Current Time=" + System.currentTimeMillis()
                + "r.status : " + response.getStatus() + "req.contextPath : " + request.getContextPath()
                + "handler: " + handler + " ex " + ex );
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            logger.info("cookie : "+ headerName);
        }


    }



}
