package com.zb.library.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * 类名：FileUtil
 * 作者： 冰
 * 创建日期： 2017/8/5 
 * 描述： 文件工具类
 *              1.文件操作模式：
 *              1.Context.MODE_PRIVATE：私有操作模式，默认模式，代表该文件是私有数据，只能被应用本身访问，
 *              在该模式下，写入的内容会覆盖源文件的内容，如果想把新写入的内容追加到原文件中，可以使用Context.MODE_APPEND
 *              2.Context.MODE_APPEND：追加操作模式：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
 *              3.Context.MODE_WORLD_READABLE：表示当前文件可以被其他应用读取。
 *              4.Context.MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
 *              如果希望文件被其他应用读和写，可以传入：
 *              openFileOutput("1234.txt", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
 *              另外，文件默认放在/data/data//files目录下
 *              对于文件的获取，Android中提供了getCacheDir()和getFilesDir()方法：
 *              getCacheDir()方法用于获取/data/data//cache目录
 *              getFilesDir()方法用于获取/data/data//files目录
 *
 **/
public class FileUtil {

    private static volatile FileUtil fileUtil;

    private Context zbContext;

    private FileUtil(Context context){
        zbContext = context;
    }

    public static FileUtil getInstacneFor(Context context){
        if(null==fileUtil){
            synchronized (FileUtil.class){
                if(fileUtil==null){
                    fileUtil = new FileUtil(context);
                }
            }
        }
        return fileUtil;
    }

    /**
     * 保存内容到文件中去
     * 冰
     * @param fileName 文件的路径
     * @param fileContent 内容
     * @param mode 模式
     *              1.文件操作模式：
     *              1.Context.MODE_PRIVATE：私有操作模式，默认模式，代表该文件是私有数据，只能被应用本身访问，
     *              在该模式下，写入的内容会覆盖源文件的内容，如果想把新写入的内容追加到原文件中，可以使用Context.MODE_APPEND
     *              2.Context.MODE_APPEND：追加操作模式：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
     *              3.Context.MODE_WORLD_READABLE：表示当前文件可以被其他应用读取。
     *              4.Context.MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
     * @return 是否保存成功
     */
    public boolean save(String fileName,String fileContent,int mode){
        try{
            if(mode==0){
                mode = Context.MODE_PRIVATE;
            }
            FileOutputStream fileOutputStream = zbContext.openFileOutput(fileName,mode);
            fileOutputStream.write(fileContent.getBytes());
            fileOutputStream.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 读取文件的内容
     * 冰
     * @param fileName 文件的路径
     * @return 返回读取的内容
     */
    public String read(String fileName){
        try{
            FileInputStream inputStream = zbContext.openFileInput(fileName);
            byte[] temp = new byte[2024];
            StringBuffer stringBuffer = new StringBuffer("");
            int len = 0;
            while ((len = inputStream.read(temp))>0){
                stringBuffer.append(new String(temp,0,len));
            }
            inputStream.close();
            return stringBuffer.toString();
        }catch (Exception e){
            return null;
        }
    }
}
