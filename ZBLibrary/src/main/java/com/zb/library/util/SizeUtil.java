package com.zb.library.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by acer on 2017/8/12.
 */

public class SizeUtil {
    private static volatile SizeUtil sizeUtil;

    private Context zbContext;

    /**
     * 屏幕的密度
     */
    private float scale;

    /**
     * 样式的屏幕的密度
     */
    private float fontScale;

    /**
     * 屏幕密度参数
     */
    private DisplayMetrics displayMetrics;

    private SizeUtil(Context context){
        zbContext = context;
        //获取屏幕密度参数
        displayMetrics = zbContext.getResources().getDisplayMetrics();
        //获取屏幕的密度
        scale = displayMetrics.density;
        //获取样式的屏幕的密度
        fontScale = displayMetrics.scaledDensity;
    }

    public static SizeUtil getInstanceFor(Context context){
        if(null == sizeUtil){
            synchronized (SizeUtil.class){
                if(sizeUtil == null){
                    sizeUtil = new SizeUtil(context);
                }
            }
        }
        return sizeUtil;
    }

    /**
     * dp转px
     * 冰
     * @param dpValue
     * @return
     */
    public int dp2px(float dpValue){
        return (int)(dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     * 冰
     * @param pxValue
     * @return
     */
    public int px2dp(float pxValue){
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     * 冰
     * @param spValue
     * @return
     */
    public int sp2px(float spValue){
        return (int)(spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     * 冰
     * @param pxValue
     * @return
     */
    public int px2sp(float pxValue){
        return (int)(pxValue / fontScale + 0.5f);
    }

    /**
     * 单位转换PX
     * 冰
     * @param typedValue
     * @param value
     * @return
     */
    public float applyDimension(int typedValue, float value){
        switch (typedValue){
            case TypedValue.COMPLEX_UNIT_PX://px转px
                return value;
            case TypedValue.COMPLEX_UNIT_DIP://dip转px
                return value * scale;
            case TypedValue.COMPLEX_UNIT_SP://sp转px
                return value * fontScale;
            case TypedValue.COMPLEX_UNIT_PT://pi转px
                return value * displayMetrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN://in转px
                return value * displayMetrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM://mm转px
                return value * displayMetrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
}
