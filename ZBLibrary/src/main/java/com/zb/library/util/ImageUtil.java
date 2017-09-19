package com.zb.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片工具类
 * 冰
 */
public class ImageUtil {

    private static volatile ImageUtil imageUtil;

    private Context zbContext;

    private ImageUtil(Context context){
        zbContext = context;
    }

    public static ImageUtil getInstanceFor(Context context){
        if(imageUtil==null){
            synchronized (ImageUtil.class){
                if(null==imageUtil){
                    imageUtil = new ImageUtil(context);
                }
            }
        }
        return imageUtil;
    }

    /**
     * 资源图片转Bitmap
     * @param resId 资源ID
     * @return Bitmap
     */
    public Bitmap resIdToBitmap(int resId){
        return BitmapFactory.decodeResource(zbContext.getResources(),resId);
    }


}
