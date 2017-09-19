package com.zb.library.load;

import android.content.Context;
import android.view.View;

/**
 *
 * 类名：ViewHelper
 * 作者： 冰
 * 创建日期： 2017/8/31
 * 描述：加载接口
 *
 **/
public interface ViewHelper {

    //获取当前布局
    View getCurrentLayout();

    //恢复以前的控件
    void restoreView();

    //显示布局
    void showLayout(View view);

    //显示布局
    void showLayout(int layoutId);

    //布局ID转控件
    View inflate(int layoutId);

    //获取布局上下文
    Context getContext();

    //获取控件
    View getView();
}
