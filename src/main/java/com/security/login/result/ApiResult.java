package com.security.login.result;

import java.io.Serializable;

/**
 * @author zhong
 */
public class ApiResult<T> {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS_CODE = "0";
    public static final String FAIL_CODE = "1";

    private String result;
    private String message;
    private T data;

    public ApiResult() {

    }

    public ApiResult(T content) {
        setResult(SUCCESS_CODE);
        setData(content);
    }

    public static <T extends Serializable> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<>();
        result.setResult(SUCCESS_CODE);
        return result;
    }

    public static <K extends Serializable> ApiResult<K> success(K data) {
        ApiResult<K> result = new ApiResult<>();
        result.setResult(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    public static <T extends Serializable> ApiResult<T> fail(String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setResult(FAIL_CODE);
        result.setMessage(message);
        return result;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
