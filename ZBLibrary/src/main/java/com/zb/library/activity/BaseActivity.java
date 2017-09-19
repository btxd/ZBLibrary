package com.zb.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.just.library.IWebLayout;
import com.zb.library.R;
import com.zb.library.bar.BarHide;
import com.zb.library.bar.ImmersionBar;
import com.zb.library.bar.OSUtils;
import com.zb.library.web.DefaultWeb;

import java.lang.ref.WeakReference;

import permissions.dispatcher.RuntimePermissions;

/**
 *
 * 类名：BaseActivity
 * 作者： 冰
 * 创建日期： 2017/8/5
 * 描述：Activity基类
 * @NeedsPermission 注释在需要权限的方法上后面，需要传入所需的权限
 * @OnShowRationale 注释在向用户解释为什么需要这个权限的方法上。equest.proceed(); 调出系统申请权限的弹窗会执行@NeedsPermissio对应的方法 request.cancel(); 会执行@OnPermissionDenied对应的方法
 * @OnPermissionDenied 被拒绝后调用的方法
 * @OnNeverAskAgain 勾选了不再提示禁止后调用的方法
 **/
@RuntimePermissions
public abstract class BaseActivity extends AppCompatActivity {

    protected Activity zbActivity;

    private WeakReference<Activity> weakReference;

    protected boolean isAutoBar = true;

    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";

    /**
     * 自定义bar
     */
    protected ImmersionBar zbAutoBar;

    protected AgentWeb zbWebView;

    protected ActionBar zbActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zbActionBar = getSupportActionBar();
        if(zbActionBar!=null){
            getSupportActionBar().hide();
        }

    }

    public void setContentView(int layoutId){
        super.setContentView(layoutId);

        weakReference = new WeakReference<Activity>(this);
        zbActivity = weakReference.get();
        if(isAutoBar){
            initAutoBar();
        }

        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景未被隐藏的问题
        if(OSUtils.isEMUI3_1()){
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor(NAVIGATIONBAR_IS_MIN),true,mNavigationStatusObserver);
            //第二种
//            zbAutoBar.navigationBarEnable(false).init();
        }

        initUI();
        disposeLogic();
        initOnClick();
    }

    public abstract void initUI();

    public abstract  void disposeLogic();

    public abstract void initOnClick();

    /**
     * 初始化自定义bar
     */
    private void initAutoBar(){
        zbAutoBar = ImmersionBar.with(this);
        zbAutoBar.statusBarColor(R.color.black).fitsSystemWindows(true).init();
    }

    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                zbAutoBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                zbAutoBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                        .fullScreen(false)
                        .init();
            }
        }
    };

    /**
     * 初始化浏览器,默认关闭进度条
     * 冰
     * @param receivedTitleCallback title回调
     * @param webChromeClient web谷歌浏览器点击监听
     * @param webViewClient web浏览器点击监听
     * @param url 去往的地址
     * @param showProgress 是否显示进度条
     */
    protected void initDefaultWeb(ChromeClientCallbackManager.ReceivedTitleCallback receivedTitleCallback, WebChromeClient webChromeClient, WebViewClient webViewClient, String url,boolean showProgress){
        if(!url.contains("https")||!url.contains("http")){
            url = "http://"+url;
        }
        if (zbWebView!=null) {
            zbWebView.getWebLifeCycle().onDestroy();
            zbWebView = null;
        }
        if(showProgress){
            zbWebView = AgentWeb.with(this)//初始化
                    .setAgentWebParent(null,new ViewGroup.LayoutParams(-1,-1))//传入父控件和参数
                    .useDefaultIndicator()//使用默认进度条
                    .defaultProgressBarColor()//使用默认进度条颜色
                    .setReceivedTitleCallback(receivedTitleCallback)//设置title回调
                    .setWebChromeClient(webChromeClient)
                    .setWebViewClient(webViewClient)
                    .setSecutityType(AgentWeb.SecurityType.strict)
                    .setWebLayout(new DefaultWeb(zbActivity))
                    .createAgentWeb()//创建web
                    .ready()//开启web
                    .go(url);//去往的地址
        }else{
            zbWebView = AgentWeb.with(this)//初始化
                    .setAgentWebParent(null,new ViewGroup.LayoutParams(-1,-1))//传入父控件和参数
                    .closeProgressBar()//关闭进度条
                    .setReceivedTitleCallback(receivedTitleCallback)//设置title回调
                    .setWebChromeClient(webChromeClient)
                    .setWebViewClient(webViewClient)
                    .setSecutityType(AgentWeb.SecurityType.strict)
                    .setWebLayout(new DefaultWeb(zbActivity))
                    .createAgentWeb()//创建web
                    .ready()//开启web
                    .go(url);//去往的地址
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(zbWebView!=null){
            if(zbWebView.handleKeyEvent(keyCode,event)){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if(zbWebView!=null){
            zbWebView.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(zbWebView!=null){
            zbWebView.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (zbWebView!=null) {
            zbWebView.getWebLifeCycle().onDestroy();
            zbWebView = null;
        }
        if(zbAutoBar!=null){
            zbAutoBar.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(zbWebView!=null){
            zbWebView.uploadFileResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
