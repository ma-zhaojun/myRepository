package com.security.login.exception;

public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException() {
        this.code = null;
    }


    public BusinessException(String message) {
        super(message);
        this.code = null;
    }


    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.code = null;
    }


    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Throwable e) {
        super(e);
        this.code = null;
    }


    public String getCode() {
        return code;
    }
}
