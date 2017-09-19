package com.zb.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zb.library.util.ImageUtil;

/**
 * tao两张图片切换
 * 冰
 */
@SuppressLint("AppCompatCustomView")
public class TabIconView extends ImageView {

    /**
     * 画笔
     */
    private Paint zbPaint;

    /**
     * 选中的图片
     */
    private Bitmap zbSelectedIcon;

    /**
     * 没有选中的图片
     */
    private Bitmap zbNormalIcon;

    /**
     * 选中时的矩阵
     */
    private Rect zbSelectedRect;

    /**
     * 没有选中的矩阵
     */
    private Rect zbNormalRect;

    /**
     * 选中时透明度;默认透明
     */
    private int zbSelectedAlpha = 0;

    public TabIconView(Context context) {
        super(context);
    }

    public TabIconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     * @param normal 没有选中的图片
     * @param selected 选中的图片
     * @param width 图片宽度
     * @param height 图片的高度
     */
    public void init(int normal,int selected,int width,int height){
        this.zbNormalIcon = ImageUtil.getInstanceFor(getContext()).resIdToBitmap(normal);
        this.zbSelectedIcon = ImageUtil.getInstanceFor(getContext()).resIdToBitmap(selected);
        this.zbNormalRect = new Rect(0,0,width,height);
        this.zbSelectedRect = new Rect(0,0,width,height);
        this.zbPaint = new Paint(1);//抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.zbPaint==null){
            return;
        }
        this.zbPaint.setAlpha(255-this.zbSelectedAlpha);
        canvas.drawBitmap(this.zbNormalIcon,null,this.zbNormalRect,this.zbPaint);
        this.zbPaint.setAlpha(this.zbSelectedAlpha);
        canvas.drawBitmap(this.zbSelectedIcon,null,this.zbSelectedRect,this.zbPaint);
    }

    /**
     * 设置选中时的透明度;越大越透明；最大255
     * @param zbSelectedAlpha 透明值
     */
    public void setZbSelectedAlpha(int zbSelectedAlpha) {
        this.zbSelectedAlpha = zbSelectedAlpha;
        invalidate();//重新绘制
    }

    /**
     * 设置选中时的透明度 百分比；范围：1-0；越大越透明
     * @param offset 透明值
     */
    public void offsetChange(float offset){
        setZbSelectedAlpha((int)(255*(1-offset)));
    }
}
