package com.zb.library.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 冰
 */
public class StringUtil {

    /**
     * 电话号码正则表达式
     */
    private final static StringBuffer rexTel = new StringBuffer("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    /**
     * 电子邮箱正在表达式
     */
    private final static StringBuffer rexEmail = new StringBuffer("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 数字正则表达式
     */
    private final static StringBuffer rexDigit = new StringBuffer("[0-9]*");

    /**
     * 判断字符串是否为空
     * 冰
     * @param str 字符串
     * @return true：空     false:不为空
     */
    public static boolean isEmpty(String str){
        StringBuffer sb = new StringBuffer(str);
        if(sb == null || "".equals(sb)){
            return true;
        }
        for(int i = 0 ; i < sb.length(); i++){
            char c = sb.charAt(i);
            if(c != ' ' && c != '\t' && c != '\r' && c != '\n'){
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转整形
     * 冰
     * @param str 字符串
     * @param defValue 整形
     * @return 转换的整形
     */
    public static int toInt(String str,int defValue){
        try{
            return Integer.parseInt(str);
        }catch(Exception e){}
        return defValue;
    }

    /**
     * 字符串转浮点型
     * 冰
     * @param str 字符串
     * @param defaultValue 默认值
     * @return 转换的浮点型
     */
    public static float toFloat(String str,float defaultValue){
        try{
            return Float.valueOf(str);
        }catch(Exception e){}
        return defaultValue;
    }

    /**
     * 字符串转布尔型
     * 冰
     * @param b 字符串
     * @param defValue 默认值
     * @return 转换的布尔型
     */
    public static boolean toBoolean(String b,boolean defValue){
        try{
            return Boolean.parseBoolean(b);
        }catch (Exception e){}
        return defValue;
    }

    /**
     * 字符串转长整形
     * 冰
     * @param obj 字符串
     * @param defValue 默认值
     * @return 转换的长整形
     */
    public static long toLong(String obj,long defValue){
        try{
            return Long.parseLong(obj);
        }catch (Exception e){}
        return defValue;
    }

    /**
     * 字符串是否JSON格式
     * 冰
     * @param json 字符串
     * @return 布尔型
     */
    public static boolean isJson(String json){
        try{
            new JSONObject(json);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 字符串判断是否是指定的正则表达式
     * 冰
     * @param str 字符串
     * @param rex 正则表达式
     * @return 布尔型
     */
    public static boolean isRex(String str,String rex){
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(rex);
        return pattern.matcher(str).matches();
    }

    /**
     * 输入流转字符串
     * 冰
     * @param is 输入流
     * @return 转换的字符串
     */
    public static String nputStreamtToString(InputStream is){
        if(is==null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try{
            StringBuffer line = new StringBuffer(bufferedReader.readLine());
            while (line != null){
                sb.append(line);
                line = new StringBuffer(bufferedReader.readLine());
            }
        }catch (Exception e){

        }finally {
            try{
                if(inputStreamReader != null){
                    inputStreamReader.close();
                    inputStreamReader = null;
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                    bufferedReader = null;
                }
            }catch (Exception e){}
        }
        return sb.toString();
    }

    /**
     * 保留两位小数的字符串，千分位补零
     * 冰
     * @param f 字符串
     * @return 转换的字符串
     */
    public static String toFloat2(float f){
        DecimalFormat decimalFormat =new DecimalFormat(",##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(f).toString();//format 返回的是字符串
    }
}
