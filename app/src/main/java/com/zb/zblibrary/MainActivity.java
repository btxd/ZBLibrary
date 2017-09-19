package com.zb.zblibrary;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.just.library.ChromeClientCallbackManager;
import com.zb.library.activity.BaseActivity;
import com.zb.library.util.ActivityUtil;
import com.zb.library.util.ViewUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ConstraintLayout content;

    private Button btn1;

    private Button btn2;

    private Button btn3;

    private Button btn4;

    private Button btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initUI() {
        content = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.content);
        btn1 = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.btn1);
        btn2 = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.btn2);
        btn3 = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.btn3);
        btn4 = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.btn4);
        btn5 = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.btn5);
    }

    @Override
    public void disposeLogic() {

    }

    @Override
    public void initOnClick() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                ActivityUtil.getInstanceFor(zbActivity).startActivity(WebActivity.class);
                break;
            case R.id.btn2:
                ActivityUtil.getInstanceFor(zbActivity).startActivity(WebProgressActivity.class);
                break;
            case R.id.btn3:
                ActivityUtil.getInstanceFor(zbActivity).startActivity(AnimationActivity.class);
                break;
            case R.id.btn4:
                ActivityUtil.getInstanceFor(zbActivity).startActivity(ViewPropertyAnimatorActivity.class);
                break;
            case R.id.btn5:
                ActivityUtil.getInstanceFor(zbActivity).startActivity(PlayActivity.class);
                break;
            default:

                break;
        }
    }
}
