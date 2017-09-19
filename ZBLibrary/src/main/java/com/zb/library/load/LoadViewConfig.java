package com.zb.library.load;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 *
 * 类名：LoadViewConfig
 * 作者： 冰
 * 创建日期： 2017/8/31
 * 描述：加载配置
 *
 **/
public class LoadViewConfig {


    private static ViewHelper viewHelper;

    private static volatile LoadViewConfig loadViewConfig;

    private LoadViewConfig(){

    }

    public static LoadViewConfig getInstanceFor(View view){

        if(null == loadViewConfig){
            synchronized (LoadViewConfig.class){
                if(loadViewConfig == null){
                    loadViewConfig = new LoadViewConfig();
                    viewHelper = new ViewHelperImpTwo(view);
                }
            }
        }
        return loadViewConfig;
    }




    /**
     * 显示错误布局
     * 冰
     */
    public void showError(View loadError){
        viewHelper.showLayout(loadError);
    }

    public void showError(@LayoutRes int loadError){
        viewHelper.showLayout(loadError);
    }

    /**
     * 显示空数据布局
     * 冰
     */
    public void showEmpty(View loadEmpty){
        viewHelper.showLayout(loadEmpty);
    }

    /**
     * 加载中布局
     * 冰
     */
    public void showLoading(View loadIng){
        viewHelper.showLayout(loadIng);
    }

    public void showLoading(@LayoutRes int loadIng){
        viewHelper.showLayout(loadIng);
    }

    /**
     * 回复以前的控件，也就是成功后的控件
     */
    public void showContent(){
        viewHelper.restoreView();
    }

}
