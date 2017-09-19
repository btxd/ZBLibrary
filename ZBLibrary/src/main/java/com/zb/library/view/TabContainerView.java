package com.zb.library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.library.R;
import com.zb.library.util.ViewUtil;

/**
 * Created by acer on 2017/8/7.
 */

public class TabContainerView extends LinearLayout {

    /**
     * 页面切换控件
     */
    private ViewPager zbViewPager;

    /**
     * 页面切换监听器
     */
    private ViewPager.OnPageChangeListener zbOnPageChangeListener;

    /**
     * 文本默认颜色
     */
    private int zbTextNormalColor;

    /**
     * 文本选中颜色
     */
    private int zbTextSelectedColor;

    /**
     * 前一次选择的位置
     */
    private int zbLastPosition;

    /**
     * 当前选中的位置
     */
    private int zbSelectedPosition;

    /**
     * 选择偏移的位置
     */
    private float zbSelectionOffset;

    /**
     * 标题数组
     */
    private String[] zbTitles;

    /**
     * icon数组
     */
    private int[][] zbIconRes;

    /**
     * item数组
     */
    private View[] zbTabView;

    /**
     * 布局文件
     */
    private int zbLayoutId;

    /**
     * 文本控件
     */
    private int zbTextViewId;

    /**
     * 图片控件
     */
    private int zbIconViewId;

    /**
     * Icon的宽度
     */
    private int zbIconWidth;

    /**
     * Icon的高度
     */
    private int zbIconHeight;

    /**
     * 是否显示过滤的颜色,默认true
     */
    private boolean zbShowTransitionColor = true;

    public TabContainerView(Context context) {
        super(context);
    }

    public TabContainerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabContainerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     * @param titles 标题数组
     * @param iconsRes 图片数组
     * @param colors 文本颜色数组
     * @param showTransitionColor  是否显示过滤颜色
     */
    public void initContainer(String[] titles,int[][] iconsRes,int[] colors,boolean showTransitionColor){
        this.zbTitles = titles;
        this.zbIconRes = iconsRes;
        this.zbTextNormalColor = getResources().getColor(colors[0]);
        this.zbTextSelectedColor = getResources().getColor(colors[1]);
        this.zbShowTransitionColor = showTransitionColor;
    }

    /**
     * 设置控件 - 图片控件和文本控件
     * @param layout 布局控件
     * @param iconId 图片控件
     * @param textId 文本控件
     * @param width 图片的宽度
     * @param height 图片的高度
     */
    public void setContainerLayout(int layout,int iconId,int textId,int width,int height){
        this.zbLayoutId = layout;
        this.zbTextViewId = textId;
        this.zbIconViewId = iconId;
        this.zbIconWidth = width;
        this.zbIconHeight = height;
    }

    /**
     * 设置控件 - 没有图片控件,只有文本控件
     * @param layout 布局控件
     * @param textId 文本控件
     * @param width 图片宽度
     * @param height 图片的高度
     */
    public void setSingleTextLayout(int layout,int textId,int width,int height){
        setContainerLayout(layout,0,textId,width,height);
    }

    /**
     * 设置空 - 没有文本控件，只有图片控件
     * @param layout 布局控件
     * @param iconId 图片控件
     * @param width 图片宽度
     * @param height 图片的高度
     */
    public void steSingleIconLayout(int layout,int iconId,int width,int height){
        setContainerLayout(layout,iconId,0,width,height);
    }

//    public void setViewPager(ViewPager viewPager){
//        //移除所有的控件
//        removeAllViews();
//        this.zbViewPager = viewPager;
//        if(viewPager != null && viewPager.getAdapter() != null){
//            viewPager.addOnPageChangeListener(new Interna);
//            addTab
//        }
//    }

    private void addTabViewToContainer(){
        PagerAdapter adapter = zbViewPager.getAdapter();
        zbTabView = new View[adapter.getCount()];

        for(int index = 0,len = adapter.getCount();index <len;index++){
            View tabView = LayoutInflater.from(getContext()).inflate(zbLayoutId,this,false);
            zbTabView[index] = tabView;

            TabIconView iconView = null;
            if (zbIconViewId > 0 ) {
                iconView = ViewUtil.getInstanceFor(getContext()).find(tabView,zbIconViewId);
                iconView.init(zbIconRes[index][0],zbIconRes[index][1],zbIconWidth,zbIconHeight);
            }

            TextView textView = null;
            if (zbTextViewId > 0) {
                textView = ViewUtil.getInstanceFor(getContext()).find(tabView,zbTextViewId);
                textView.setText(zbTitles[index]);
            }

            LayoutParams layoutParams = (LayoutParams) tabView.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.height = 1;


        }
    }

    /**
     * item 点击事件
     * @param view 控件
     * @param position
     */
    private void addTabOnClickListener(View view, final int position){
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                zbViewPager.setCurrentItem(position,false);
            }
        };
        view.setOnClickListener(listener);
    }
}
