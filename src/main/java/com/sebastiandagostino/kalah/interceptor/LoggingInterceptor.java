package com.sebastiandagostino.kalah.interceptor;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(LoggingInterceptor.class);

    private static final String METHOD = "Method: ";
    private static final String URI = "URI: ";
    private static final String STATUS = "Status: ";
    private static final String REMOTE_ADDR = "Remote Address: ";
    private static final String SEPARATOR = " | ";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append(METHOD).append(request.getMethod()).append(SEPARATOR);
        logMessage.append(URI).append(request.getRequestURI()).append(SEPARATOR);
        logMessage.append(STATUS).append(response.getStatus()).append(SEPARATOR);
        logMessage.append(REMOTE_ADDR).append(request.getRemoteAddr());
        if (response.getStatus() >= HttpStatus.BAD_REQUEST.value()) {
            logger.error(logMessage.toString(), ex);
        } else {
            logger.info(logMessage.toString(), ex);
        }
    }

}
