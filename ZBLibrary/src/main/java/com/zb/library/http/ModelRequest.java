package com.zb.library.http;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.zb.library.model.HttpHeaderModel;

/**
 *
 * 类名：ModelRequest
 * 作者： 冰
 * 创建日期： 2017/8/12
 * 描述：自定义实体类请求 规定的头部
 *
 **/
public class ModelRequest<T> extends ModelUtilRequest {

    private Class<T> zbClass;

    public ModelRequest(String url) {
        super(url);
    }

    public ModelRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    public ModelRequest(String url, RequestMethod requestMethod, Class zbClass) {
        super(url, requestMethod, HttpHeaderModel.class);
        this.zbClass = zbClass;
    }

    @Override
    public Object parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        HttpHeaderModel httpHeaderModel = (HttpHeaderModel) super.parseResponse(responseHeaders, responseBody);
        return JSON.parseObject((String) httpHeaderModel.getData());
    }
}
