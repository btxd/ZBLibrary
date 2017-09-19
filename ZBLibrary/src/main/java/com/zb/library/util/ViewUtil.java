package com.zb.library.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by acer on 2017/8/7.
 */

public class ViewUtil {
    private volatile static ViewUtil viewUtil;

    private static Context zbContext;

    private ViewUtil(){

    }

    public static ViewUtil getInstanceFor(Context context){
        zbContext = context;
        if (null == viewUtil) {
            synchronized (ViewUtil.class){
                if(viewUtil == null){
                    viewUtil = new ViewUtil();
                }
            }
        }
        return viewUtil;
    }

    /**
     * 获取控件
     * @param pview 父控件ID
     * @param cid 子控件ID
     * @param <T>
     * @return
     */
    public <T extends View> T find(View pview,int cid){
        if(pview == null || cid <= 0){
            throw new NullPointerException("父控件找不到或者子控件的id找不到");
        }
        View view = pview.findViewById(cid);
        if(view == null){
            throw new NullPointerException("找不到该控件ID");
        }
        return (T)view;
    }

    /**
     * 获取当前控件
     *
     * @param cid 子控件ID
     * @param <T>
     * @return
     */
    public <T extends View> T findWindow(int cid){
        View pview = ((Activity)zbContext).getWindow().getDecorView();
        return find(pview,cid);
    }

    /**
     * 获取当前控件
     * @param idName 子控件ID名字
     * @param <T>
     * @return
     */
    public <T extends View> T findWindow(String idName){
        int cid = zbContext.getResources().getIdentifier(idName,"id",zbContext.getPackageName());
        return findWindow(cid);
    }

    /**
     * 获取控件
     * @param pview 父控件
     * @param idName 子控件ID名字
     * @param <T>
     * @return
     */
    public <T extends  View> T find(View pview,String idName){
        int cid = zbContext.getResources().getIdentifier(idName,"id",zbContext.getPackageName());
        return find(pview,cid);
    }

    /**
     * 返回LayoutInflate
     * 冰
     * @return
     */
    public LayoutInflater inflate(){
        LayoutInflater inflater = LayoutInflater.from(zbContext);
        return inflater;
    }
}
