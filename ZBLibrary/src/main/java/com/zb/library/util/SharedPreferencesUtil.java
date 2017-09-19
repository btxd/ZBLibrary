package com.zb.library.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * SharePreference工具类
 * 冰
 */
public class SharedPreferencesUtil {

    /**
     * 保存在手机的名字
     */
    public static String SHAREPF = "zb_sp";

    private static volatile SharedPreferencesUtil sharedPreferencesUtil;

    private Context zbContext;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private SharedPreferencesUtil(Context context){
        zbContext = context;
    }

    public static SharedPreferencesUtil getInstanceFor(Context context){
        if(null==sharedPreferencesUtil){
            synchronized (SharedPreferencesUtil.class){
                if(sharedPreferencesUtil==null){
                    sharedPreferencesUtil = new SharedPreferencesUtil(context);
                }
            }
        }
        return sharedPreferencesUtil;
    }

    /**
     * 初始化
     * 冰
     * @param fileName 保存的文件名
     * @param mode 共四种模式：
     *             Context.MODE_PRIVATE    =  0     为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中
     *             Context.MODE_APPEND    =  32768     模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
     *             Context.MODE_WORLD_READABLE =  1     表示当前文件可以被其他应用读取
     *             Context.MODE_WORLD_WRITEABLE =  2        表示当前文件可以被其他应用写入
     * @return
     */
    public SharedPreferencesUtil init(String fileName,int mode){
        if(!StringUtil.isEmpty(fileName)){
            SHAREPF = fileName;
        }
        if(mode==0){
            sharedPreferences = zbContext.getSharedPreferences(SHAREPF,zbContext.MODE_PRIVATE);
        }else{
            sharedPreferences = zbContext.getSharedPreferences(SHAREPF,mode);
        }

        editor = sharedPreferences.edit();
        return this;
    }

    /**
     * 保存数据
     * 冰
     * @param key 键
     * @param obj 值
     * @return 是否保存成功
     */
    public boolean put(String key ,Object obj){
        if(editor==null){
            throw new NullPointerException("请先初始化：init");
        }
        if(StringUtil.isEmpty(key)||obj==null){
            return false;
        }
        if(obj instanceof Boolean){
            editor.putBoolean(key,(Boolean)obj);
        }else if(obj instanceof Float){
            editor.putFloat(key,(Float)obj);
        }else if(obj instanceof  Integer){
            editor.putInt(key,(Integer) obj);
        }else if(obj instanceof  Long){
            editor.putLong(key,(Long)obj);
        }else if(obj instanceof String){
            editor.putString(key,(String)obj);
        }else{
            return false;
        }
        editor.commit();
        return true;
    }

    /**
     * 获取数据
     * 冰
     * @param key 键
     * @param defObj 值
     * @return null：获取失败；其他成功
     */
    public Object get(String key,Object defObj){
        if(sharedPreferences==null){
            throw new NullPointerException("请先初始化：init");
        }
        if(StringUtil.isEmpty(key)||defObj==null){
            return null;
        }
        if(defObj instanceof Boolean){
            return sharedPreferences.getBoolean(key,(Boolean)defObj);
        }else if(defObj instanceof Float){
            return sharedPreferences.getFloat(key,(Float)defObj);
        }else if(defObj instanceof Integer){
            return sharedPreferences.getInt(key,(Integer)defObj);
        } else if (defObj instanceof  Long) {
            return sharedPreferences.getLong(key,(Long)defObj);
        } else if (defObj instanceof  String) {
            return sharedPreferences.getString(key,(String)defObj);
        }else{
            return null;
        }
    }

    public void remove(String key){
        if(editor==null){
            throw new NullPointerException("请先初始化：init");
        }
        editor.remove(key);
        editor.commit();
    }

    /**
     * 获取所有保存的数据
     * 冰
     * @return map类型
     */
    public Map<String,?> getAll(){
        if(sharedPreferences==null){
            throw new NullPointerException("请先初始化：init");
        }
        Map<String,?> map = sharedPreferences.getAll();
        return map;
    }

    /**
     * 清除所有保存的数据
     * 冰
     */
    public void clear(){
        if(editor==null){
            throw new NullPointerException("请先初始化：init");
        }
        editor.clear();
        editor.commit();
    }

    /**
     * 检查key数据是否存在
     * 冰
     * @param key 键
     * @return 是否存在
     */
    public boolean contains(String key){
        if(sharedPreferences==null){
            throw new NullPointerException("请先初始化：init");
        }
        return sharedPreferences.contains(key);
    }
}
