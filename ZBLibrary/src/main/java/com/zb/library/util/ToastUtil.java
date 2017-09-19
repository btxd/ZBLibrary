package com.zb.library.util;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.zb.library.R;

import java.lang.ref.WeakReference;

/**
 *
 * 类名：ToastUtil
 * 作者： 冰
 * 创建日期： 2017/8/28
 * 描述：土司工具类
 *
 **/
public class ToastUtil {
    private volatile static ToastUtil toastUtil;

    private Context zbContext;

    private static final int DEFAULT_COLOR = 0x12000000;

    private static final Handler zbHander = new Handler(Looper.getMainLooper());

    private Toast zbToast;

    private WeakReference<View> zbViewWeakReference;

    private int zbGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private int zbXOffset = 0;

    private int zbYOffset = 0;

    private int zbBackgroundColor = DEFAULT_COLOR;

    private int zbBgResource = -1;

    private int zbMessageColor = DEFAULT_COLOR;

    private ToastUtil(Context context){
        zbContext = context;
        zbYOffset = (int)(64*zbContext.getResources().getDisplayMetrics().density+0.5);
    }

    public static ToastUtil getInstanceFor(Context context){
        if(toastUtil == null){
            synchronized (ToastUtil.class){
                if(null == toastUtil){
                    toastUtil = new ToastUtil(context);
                }
            }
        }
        return toastUtil;
    }


    /**
     * 设置土司的位置
     * 冰
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public ToastUtil setGravity(int gravity,int xOffset,int yOffset){
        zbGravity = gravity;
        zbXOffset = xOffset;
        zbYOffset = yOffset;
        return this;
    }

    /**
     * 自定义土司布局
     * 冰
     * @param layoutId 布局控件ID
     */
    public ToastUtil setView(@LayoutRes int layoutId){
        LayoutInflater inflater = (LayoutInflater) zbContext.getSystemService(zbContext.LAYOUT_INFLATER_SERVICE);
        zbViewWeakReference = new WeakReference<View>(inflater.inflate(layoutId,null));
        return this;
    }

    /**
     * 自定义土司控件
     * 冰
     * @param view 布局控件
     */
    public ToastUtil setView(View view){
        zbViewWeakReference = view == null ? null : new WeakReference<View>(view);
        return this;
    }

    /**
     * 获取自定义控件
     * 冰
     * @return
     */
    public View getView(){
        if (zbViewWeakReference != null) {
            View view = zbViewWeakReference.get();
            if (view != null) {
                return view;
            }
        }
        if (zbToast != null) {
            return zbToast.getView();
        }
        return null;
    }

    /**
     * 设置背景颜色
     * 冰
     * @param backgroundColor 颜色
     */
    public ToastUtil setBgColor(@ColorInt int backgroundColor){
        zbBackgroundColor = backgroundColor;
        return this;
    }

    /**
     * 设置背景资源
     * 冰
     * @param bgResource 背景资源
     */
    public ToastUtil setBgResource(@DrawableRes int bgResource){
        this.zbBgResource = bgResource;
        return this;
    }

    /**
     * 设置字体的颜色
     * 冰
     * @param messageColor 颜色
     */
    public ToastUtil setMessageColor(@ColorInt int messageColor){
        zbMessageColor = messageColor;
        return this;
    }

    /**
     * 短时间显示土司
     * 冰
     * @param text 内容
     */
    public void showShortToast(@NonNull final CharSequence text){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                show(text,Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 短时间显示土司
     * 冰
     * @param resId 资源ID
     */
    public void showShortToast(@StringRes final int resId){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                show(resId,Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 短时时间显示土司
     * 冰
     * @param resId 资源ID 格式
     * @param args 参数
     */
    public void showShortToast(@StringRes final int resId, final Object... args){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                show(resId,Toast.LENGTH_SHORT,args);
            }
        });
    }

    /**
     * 短时间显示土司
     * 冰
     * @param format 字符串 格式
     * @param args 参数
     */
    public void showShortToast(final String format, final Object... args){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                show(format,Toast.LENGTH_SHORT,args);
            }
        });
    }

    /**
     * 长时间显示土司
     * 冰
     * @param text 文本
     */
    public void showLongToast(@NonNull final CharSequence text){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                show(text,Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 长时间显示土司
     * 冰
     * @param resId 资源ID
     */
    public void showLongToast(@StringRes final int resId){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                show(resId,Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 长时间显示土司
     * 冰
     * @param resId 资源ID 格式
     * @param args 参数
     */
    public void showLongToast(@StringRes final int resId, final Object... args){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                showLongToast(resId,Toast.LENGTH_LONG,args);
            }
        });
    }

    /**
     * 长时间显示土司
     * 冰
     * @param format 字符串 格式
     * @param args 参数
     */
    public void showLongToast(final String format, final Object... args){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                show(format,Toast.LENGTH_LONG,args);
            }
        });
    }

    /**
     * 短时间显示自定义土司
     * 冰
     * @param layoutId 控件ID
     */
    public void showCustomShortToast(@LayoutRes final int layoutId){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                setView(layoutId);
                show("",Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 长时间显示自定义土司
     * 冰
     * @param layoutId 控件ID
     */
    public void showCustomShort(@LayoutRes final int layoutId){
        zbHander.post(new Runnable() {
            @Override
            public void run() {
                setView(layoutId);
                show("",Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 显示土司
     * 冰
     * @param resId 资源ID 格式
     * @param duration 时间
     * @param args 参数
     */
    private void show(@StringRes int resId,int duration,Object... args){
        show(String.format(zbContext.getString(resId),args),duration);
    }

    /**
     * 显示土司
     * 冰
     * @param format 格式
     * @param duration 时间
     * @param args 参数
     */
    private void show(String format,int duration,Object... args){
        show(String.format(format,args),duration);
    }

    /**
     *显示土司
     * 冰
     * @param text 文本
     * @param duration 时间
     */
    private void show(CharSequence text,int duration){
        cancel();
        if (zbViewWeakReference != null) {
            View view = zbViewWeakReference.get();
            if (view != null) {
                zbToast = new Toast(zbContext);
                zbToast.setView(view);
                zbToast.setDuration(duration);
            }
        }else{
            if(zbMessageColor != DEFAULT_COLOR){
                //span字符串
                SpannableString spannableString = new SpannableString(text);
                //前景颜色span
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(zbMessageColor);
                //设置字符串前景色，背景色
                spannableString.setSpan(foregroundColorSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                zbToast = Toast.makeText(zbContext,spannableString,duration);
            }else{
                zbToast = Toast.makeText(zbContext,text,duration);
            }
        }

        View view = zbToast.getView();
        if (zbBgResource != -1) {
            view.setBackgroundResource(zbBgResource);
        }else if(zbBackgroundColor != DEFAULT_COLOR){
            view.setBackgroundColor(zbBackgroundColor);
        }
        zbToast.setGravity(zbGravity,zbXOffset,zbYOffset);
        zbToast.show();
    }

    /**
     * 取消土司
     * 冰
     */
    public void cancel(){
        if(zbToast != null){
            zbToast.cancel();
            zbToast = null;
        }
    }
}
