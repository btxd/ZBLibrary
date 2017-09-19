package com.zb.library.web;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.just.library.IWebLayout;
import com.zb.library.R;
import com.zb.library.util.ViewUtil;



/**
 *
 * 类名：DefaultWeb
 * 作者： 冰
 * 创建日期： 2017/9/11
 * 描述：默认浏览器布局
 *
 **/
public class DefaultWeb implements IWebLayout {
    private Activity mActivity;
    private WebView mWebView = null;

    public DefaultWeb(Activity activity) {
        this.mActivity = activity;
        mWebView = (WebView) ViewUtil.getInstanceFor(activity).inflate().inflate(R.layout.default_web_layout, null);

    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mWebView;
    }

    @Nullable
    @Override
    public WebView getWeb() {
        return mWebView;
    }
}
