package com.security.login.result;

import java.util.List;

/**
 * Created by zhong on 2018/6/11.
 */
public class ListResult<T> extends ApiResult<List<T>> {

    public ListResult() {
    }

    public ListResult(List<T> data) {
        setResult(SUCCESS_CODE);
        setData(data);
    }

    public static <K extends Object> ListResult<K> success(List<K> data) {
        ListResult<K> result = new ListResult<>();
        result.setResult(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    public static <T extends Object> ListResult<T> failListResult(String message) {
        ListResult<T> result = new ListResult<>();
        result.setResult(FAIL_CODE);
        result.setMessage(message);
        return result;
    }
}
