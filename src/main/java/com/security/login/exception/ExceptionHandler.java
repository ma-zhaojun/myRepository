package com.security.login.exception;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandler {
    private ExceptionHandler() {

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    public static final String DEFAULT_ERROR_MESSAGE = "系统错误";

    public static String getErrorMessage(Throwable ex) {
        return getErrorMessage(ex, DEFAULT_ERROR_MESSAGE);
    }

    public static String getErrorMessage(Throwable ex, String defaultErrorMessage) {
        if (ex == null) {
            return defaultErrorMessage;
        }
        if (ex instanceof BusinessException) {
            return ex.getMessage();
        } else {
            return defaultErrorMessage;
        }
    }

    public static void exceptionToResponse(BusinessException ex, HttpServletResponse response) {
        try {
            response.setStatus(400);
            if (StringUtils.isNotEmpty(ex.getMessage())) {
                response.getWriter().write(StringEscapeUtils.escapeHtml3(ex.getMessage()));
            }
        } catch (IOException e) {
            LOGGER.info("输出数据错误", e);
        }
    }
}
