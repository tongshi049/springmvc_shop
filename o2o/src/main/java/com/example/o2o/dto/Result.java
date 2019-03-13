package com.example.o2o.dto;

/**
 * wrap json obj, all return results can use it
 * @param <T>
 */
public class Result<T> {

    private boolean success; // indicates if successed

    private T data; // returned data

    private String errorMsg; // error message

    private int errorCode;

    public Result(){}

    // constructor when operation is successful
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    // constructor when operation is failed
    public Result(boolean success, int errorCode, String errorMsg) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
