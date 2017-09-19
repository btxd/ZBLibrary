package com.zb.library.load;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by acer on 2017/8/30.
 */

public class ViewHelperImpTwo implements ViewHelper {

    private ViewHelper helper;

    private View view;

    public ViewHelperImpTwo(View view){
        super();
        this.view = view;
        //获取父控件
        ViewGroup group = (ViewGroup) view.getParent();
        //获取控件的配置
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //帧布局
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        if (group != null) {
            Log.e("zhoubing","呵呵");
            //移除控件
            group.removeView(view);
            //添加帧布局
            group.addView(frameLayout,layoutParams);
        }

        //配置参数，宽高最大
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //控件
        View floatView = new View(view.getContext());
        //添加view布局
        frameLayout.addView(view,params);
        //添加floatview布局
        frameLayout.addView(floatView,params);
        helper = new ViewHelperImp(floatView);
    }

    @Override
    public View getCurrentLayout() {
        return helper.getCurrentLayout();
    }

    @Override
    public void restoreView() {
        helper.showLayout(view);
    }

    @Override
    public void showLayout(View view) {
        helper.showLayout(view);
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return helper.inflate(layoutId);
    }

    @Override
    public Context getContext() {
        return helper.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
