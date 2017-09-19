package com.zb.library.load;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by acer on 2017/8/29.
 */

public class ViewHelperImp implements ViewHelper {

    //传入控件
    private View view;

    //父控件
    private ViewGroup parentView;

    //传入控件位置
    private int viewIndex;

    //控件参数
    private ViewGroup.LayoutParams params;

    //当前控件
    private View currentView;

    public ViewHelperImp(View view){
        super();
        //赋值view
        this.view = view;
    }

    /**
     * 初始化
     */
    private void init(){
        //获取传入控件的参数
        params = view.getLayoutParams();
        //判断控件的父控件的是否为空
        if(view.getParent() != null){
            Log.e("zhoubing","获取父控件");
            //获取父控件
            parentView = (ViewGroup) view.getParent();
        }else{
            Log.e("zhoubing","获取更布局");
            //获取根布局
//            parentView = view.getRootView().findViewById(android.R.id.content);
        }
        //获取父控件的子控件大小
        int count = parentView.getChildCount();
        //遍历子控件
        for(int index = 0;index < count;index++){
            //判断是否传入的控件
            if (view == parentView.getChildAt(index)) {
                //获取传入控件的位置
                viewIndex = index;
                break;
            }
        }
        //当前控件为传入的控件
        currentView = view;
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        showLayout(view);
    }

    @Override
    public synchronized void showLayout(View view) {
        //判断父控件是否为空
        if (parentView == null) {
            //初始化
            init();
        }


//        //当前控件为传入的控件
//        this.currentView = view;
//        //判断view是否传入的view一致
//        if (parentView.getChildAt(viewIndex) != view) {
//
//            //获取父控件
//            ViewGroup parent = (ViewGroup) view.getParent();
//            //判断父控件是否为空
//            if(parent != null){
//                //父控件移除当前控件
//                parent.removeView(view);
//            }
//            //夫控件移除所有子控件
//            parentView.removeAllViews();
//        }
        parentView.removeAllViews();
        //父控件添加当前控件覆盖以前的
        parentView.addView(view,params);
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(view.getContext()).inflate(layoutId,null);
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
