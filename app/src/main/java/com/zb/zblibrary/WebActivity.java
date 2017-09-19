package com.zb.zblibrary;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.just.library.ChromeClientCallbackManager;
import com.zb.library.activity.BaseActivity;
import com.zb.library.util.ViewUtil;

public class WebActivity extends BaseActivity {

    private ConstraintLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
    }

    @Override
    public void initUI() {
        ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.content);
    }

    @Override
    public void disposeLogic() {
        initDefaultWeb( new ChromeClientCallbackManager.ReceivedTitleCallback() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }
        }, new WebChromeClient(), new WebViewClient(), "https://m.jd.com/",false);

    }

    @Override
    public void initOnClick() {

    }
}
