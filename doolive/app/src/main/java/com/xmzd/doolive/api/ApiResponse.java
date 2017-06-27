package com.xmzd.doolive.api;

/**
 * Date    2017/6/26
 * Author  xiaomao
 */

public class ApiResponse<T> {

    private int state;
    private String message;
    private T data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public boolean isSuccess() {
        return state == 200;
    }
}
