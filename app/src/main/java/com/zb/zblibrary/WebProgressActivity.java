package com.zb.zblibrary;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.just.library.ChromeClientCallbackManager;
import com.just.library.ChromeClientProgress;
import com.zb.library.activity.BaseActivity;
import com.zb.library.util.ViewUtil;

public class WebProgressActivity extends BaseActivity {

    private ConstraintLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_progress);
    }

    @Override
    public void initUI() {
//        ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.contentp);
    }

    @Override
    public void disposeLogic() {
        initDefaultWeb( new ChromeClientCallbackManager.ReceivedTitleCallback() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }
        }, new WebChromeClient(), new WebViewClient(), "www.baidu.com",true);
    }

    @Override
    public void initOnClick() {

    }

}
