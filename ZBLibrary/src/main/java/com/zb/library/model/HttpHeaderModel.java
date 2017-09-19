package com.zb.library.model;

import java.io.Serializable;

/**
 * Created by acer on 2017/8/12.
 */

public class HttpHeaderModel<T> implements Serializable{
    private String status;
    private String msg;
    private String code;
    private T data;

    public HttpHeaderModel(String status, String msg, String code, T data) {
        this.status = status;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public HttpHeaderModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
