package com.zb.zblibrary.model;

/**
 * Created by acer on 2017/9/12.
 */

public class SwitchVideoModel {

    private String url;

    private String name;

    public SwitchVideoModel() {
    }

    public SwitchVideoModel(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
