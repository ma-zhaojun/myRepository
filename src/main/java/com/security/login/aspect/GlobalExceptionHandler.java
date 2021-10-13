package com.security.login.aspect;

import com.security.login.exception.BusinessException;
import com.security.login.result.ApiResult;
import com.security.login.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR_CONTROLLER = "controller发生异常";

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ApiResult<String> methodErrorErrorHandler(HttpRequestMethodNotSupportedException ex) {
        logger.info(ex.getMessage());
        return ApiResult.fail(ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ApiResult<String> defaultErrorHandler(Exception ex) {
        if (ex instanceof BusinessException) {
            logger.info(Util.getErrorMsgWithTrace(ex));
            ApiResult.fail(ex.getMessage());
        } else {
            logger.info(ERROR_CONTROLLER, ex);
            ApiResult.fail(ERROR_CONTROLLER);
        }
        return ApiResult.fail(com.security.login.exception.ExceptionHandler.getErrorMessage(ex));
    }
}
