package com.zb.library.http;

import android.content.Context;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.zb.library.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acer on 2017/8/12.
 */

public class HttpManager {
    private volatile static HttpManager httpManager;

    private Context zbContext;

    private RestRequest restRequest;

    private RequestQueue requestQueue;

    private String url;

    private CacheMode cacheMode;


    private HttpManager(Context context){
        zbContext = context;
        requestQueue = NoHttp.newRequestQueue(3);
    }

    public static HttpManager getInstanceFor(Context context){
        if(httpManager == null){
            synchronized (HttpManager.class){
                if (null == httpManager) {
                    httpManager = new HttpManager(context);
                }
            }
        }
        return httpManager;
    }

    public void requestModel(String url, RequestMethod method,Class cls,CacheMode cacheMode){
        this.url = url;
        this.cacheMode = cacheMode;
        restRequest = new ModelRequest(url,method,cls);
    }

    public void addParams(int flag, HashMap<String,Object> params, final HttpCallBackListener httpCallBackListener){
        for(Map.Entry<String,Object> entry:params.entrySet()){
            if(!StringUtil.isEmpty(entry.getValue().toString())){
                this.url += entry.getValue().toString();
                restRequest.add(entry.getKey(), entry.getValue().toString());
            }
        }
        restRequest.setCacheMode(this.cacheMode);
        restRequest.setCacheKey(this.url);
        requestQueue.add(flag, restRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                httpCallBackListener.onSuccess(what,response.get());
            }

            @Override
            public void onFailed(int what, Response response) {
                httpCallBackListener.onFail(what);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 请求队列取消
     * 冰
     */
    public void cancelAll(){
        requestQueue.cancelAll();
    }

    /**
     * 请求队列停止
     * 冰
     */
    public void onStop(){
        requestQueue.stop();
    }

    /**
     * 请求队列开始
     * 冰
     */
    public void onStart(){
        requestQueue.start();
    }

    public interface HttpCallBackListener{
        void onSuccess(int what,Object obj);
        void onFail(int what);
    }

}
