package com.zb.library.http;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 *
 * 类名：ModelUtilRequest
 * 作者： 冰
 * 创建日期： 2017/8/12
 * 描述：实体请求通用
 *
 **/
public class ModelUtilRequest<T> extends RestRequest<T> {

    private Class<T> zbClass;

    public ModelUtilRequest(String url) {
        super(url);
    }

    public ModelUtilRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    public ModelUtilRequest(String url, RequestMethod requestMethod,Class<T> zbClass) {
        super(url, requestMethod);
        this.zbClass = zbClass;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String response = StringRequest.parseResponseString(responseHeaders,responseBody);
        return JSON.parseObject(response,zbClass);
    }
}
