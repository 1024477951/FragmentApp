package com.fragmentapp.http;

/**
 * Created by liuzhen on 2017/11/3.
 */

public class BaseResponses<T> {

    private String info;
    private int status;
    private T data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
