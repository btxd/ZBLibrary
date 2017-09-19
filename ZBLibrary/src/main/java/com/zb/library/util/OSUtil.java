package com.zb.library.util;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 *
 * 类名：OSUtil
 * 作者： 冰
 * 创建日期： 2017/8/14
 * 描述：系统判断工具类
 *
 **/
public class OSUtil {
    private static volatile OSUtil osUtil;

    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";
    private static final String KEY_DISPLAY = "ro.build.display.id";

    private OSUtil(){}

    public static OSUtil getInstanceFor(){
        if(null == osUtil){
            synchronized (OSUtil.class){
                if(osUtil == null){
                    osUtil = new OSUtil();
                }
            }
        }
        return osUtil;
    }

    /**
     * 判断系统是否miui系统
     * 冰
     * @return
     */
    public boolean isMIUI(){
        String property = getSystemProperty(KEY_MIUI_VERSION_NAME,"");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断miui版本是否大于等于6
     * 冰
     * @return
     */
    public boolean isMIUI6Later(){
        String version = getMIUIVersion();
        if((!version.isEmpty()&&Integer.valueOf(version.substring(1))>=6)){
            return true;
        }
        return false;
    }

    /**
     * 获取miui系统的版本
     * 冰
     * @return
     */
    public String getMIUIVersion(){
        return isEMUI()?getSystemProperty(KEY_MIUI_VERSION_NAME,""):"";
    }

    /**
     * 判断系统是否emui
     * 冰
     * @return
     */
    public boolean isEMUI(){
        String property = getSystemProperty(KEY_EMUI_VERSION_NAME,"");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 获取emui系统版本
     * @return
     */
    public String getEMUIVersion(){
        return isEMUI()?getSystemProperty(KEY_EMUI_VERSION_NAME,""):"";
    }

    /**
     * 判断是否为emui3.1版本
     * 冰
     * @return
     */
    public boolean isEMUI3_1(){
        String property = getEMUIVersion();
        if("EmotionUI 3".equals(property) || property.contains("EmotionUI_3.1")){
            return true;
        }
        return false;
    }

    /**
     * 判断是否为flymeOS系统
     * 冰
     * @return
     */
    public boolean isFlymeOS(){
        return getFlymeOSFlag().toLowerCase().contains("flyme");
    }

    /**
     * 判断flymeOS的版本是否大于等于4
     * 冰
     * @return
     */
    public boolean isFlymeOS4Later(){
        String version = getFlymeOSVersion();
        int num;
        if (!version.isEmpty()) {
            if(version.toLowerCase().contains("os")){
                num = Integer.valueOf(version.substring(9,10));
            }else{
                num = Integer.valueOf(version.substring(6,7));
            }
            if (num >= 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断flymeOS 的版本是否等于5
     * 冰
     * @return
     */
    public boolean isFlymeOS5(){
        String version = getFlymeOSVersion();
        int num;
        if(!version.isEmpty()){
            if(version.toLowerCase().contains("os")){
                num = Integer.valueOf(version.substring(9,10));
            }else{
                num = Integer.valueOf(version.substring(6,7));
            }
            if(num == 5){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取flymeOS的版本
     * 冰
     */
    private String getFlymeOSVersion(){
        return isFlymeOS()?getSystemProperty(KEY_DISPLAY,""):"";
    }

    /**
     * 获取系统标识
     * 冰
     * @return
     */
    private String getFlymeOSFlag(){
        return getSystemProperty(KEY_DISPLAY,"");
    }

    /**
     * 获取系统SystemProperties中get方法
     * 冰
     * @param key
     * @param defaultValue
     * @return
     */
    private String getSystemProperty(String key ,String defaultValue){
        try{
            //反射类
            Class<?> clz = Class.forName("android.os.SystemProperties");
            //反射该类的方法
            Method get = clz.getMethod("get",String.class,String.class);
            //执行该方法
            return (String)get.invoke(clz,key,defaultValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return defaultValue;
    }
}
