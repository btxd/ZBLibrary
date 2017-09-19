package com.zb.library.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 类名：ActivityUtil
 * 作者： 冰
 * 创建日期： 2017/8/28
 * 描述：Activity工具类
 *
 **/
public class ActivityUtil {

    private volatile static ActivityUtil activityUtil;

    private Context zbContext;

    private ActivityUtil(Context context){
        zbContext = context;
    }

    public static ActivityUtil getInstanceFor(Context context){
        if(null == activityUtil){
            synchronized (ActivityUtil.class){
                activityUtil = new ActivityUtil(context);
            }
        }
        return activityUtil;
    }

    /**
     * 判断是否存在Activity
     * 冰
     * @param packageName 包名
     * @param className activity 全路径名
     * @return
     */
    public boolean isActivityExists(@NonNull String packageName,@NonNull String className){
        Intent intent = new Intent();
        intent.setClassName(packageName,className);
        return !(zbContext.getPackageManager().resolveActivity(intent,0)==null||intent.resolveActivity(zbContext.getPackageManager())==null||zbContext.getPackageManager().queryIntentActivities(intent,0).size()==0);
    }

    /**
     * 跳转Activity
     * 冰
     * @paam cls 类名
     */
    public void startActivity(@NonNull Class<?> cls){
        startActivity(null,zbContext.getPackageName(),cls.getName());
    }

    /**
     * 跳转Activity
     * 冰
     * @param cls 类
     * @param enterAnim 进入动画
     * @param exitAnim 出去动画
     */
    public void startActivity(@NonNull Class<?> cls, @AnimRes int enterAnim,@AnimRes int exitAnim){
        startActivity(null,zbContext.getPackageName(),cls.getName());
        ((Activity)zbContext).overridePendingTransition(enterAnim,exitAnim);
    }

    /**
     * 跳转Activity
     * 冰
     * @param extras 参数
     * @param cls 类
     */
    public void startActivity(@NonNull Bundle extras,@NonNull Class<?> cls){
        startActivity(extras,zbContext.getPackageName(),cls.getName());
    }

    /**
     * 跳转Activity
     * 冰
     * @param extras 参数
     * @param cls 类
     * @param enterAnim 进入动画
     * @param exitAnim 出去动画
     */
    public void startActivity(@NonNull Bundle extras,@NonNull Class<?> cls,@AnimRes int enterAnim,@AnimRes int exitAnim){
        startActivity(extras,zbContext.getPackageName(),cls.getName());
        ((Activity)zbContext).overridePendingTransition(enterAnim,exitAnim);
    }

    /**
     * 跳转Activity
     * 冰
     * @param extras 参数
     * @param pkg 包名
     * @param cls 类全路径名
     */
    private void startActivity(Bundle extras,String pkg,String cls){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(extras != null){
            //设置参数
            intent.putExtras(extras);
        }
        //设置调转类，第一个参数：包名；第二个参数：全路径类名
        intent.setComponent(new ComponentName(pkg,cls));
        if (!(zbContext instanceof Activity)) {
            //判断是否开启或者存在，如果开启或者存在就直接放到栈前
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        zbContext.startActivity(intent);
    }

    /**
     * 获取Launcher Activity
     * @param packageName 包名
     * @return
     */
    public String getLauncherActivity(@NonNull String packageName){
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager packageManager = zbContext.getPackageManager();
        //查找
        List<ResolveInfo> infoList = packageManager.queryIntentActivities(intent,0);
        for(ResolveInfo info:infoList){
            if(info.activityInfo.packageName.equals(packageName)){
                return info.activityInfo.name;//activity名
            }
        }
        return "no "+packageName;
    }

    /**
     * 获取栈顶Activity
     * 冰
     * @return
     */
    public Activity getTopActivity(){
        try{
            //反射
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities;
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
                activities = (HashMap) activitiesField.get(activityThread);
            }else{
                activities = (ArrayMap) activitiesField.get(activitiesField);
            }

            for(Object activityRecord:activities.values()){
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if(!pausedField.getBoolean(activityRecord)){
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activitiesField.setAccessible(true);
                    return (Activity)activitiesField.get(activityRecord);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
