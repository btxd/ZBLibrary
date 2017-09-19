package com.zb.zblibrary.video;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zb.library.util.ToastUtil;
import com.zb.zblibrary.R;
import com.zb.zblibrary.model.SwitchVideoModel;
import com.zb.zblibrary.view.SwitchVideoTypeDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by acer on 2017/9/12.
 */

public class SampleVide extends StandardGSYVideoPlayer {

    private TextView mMoreScale;

    private TextView mSwitchSize;

    private TextView mChangeRotate;

    private TextView mChangeTransform;

    private List<SwitchVideoModel> mUrlList = new ArrayList<>();

    private int mType = 0;

    private int mTransformSize = 0;

    private int mSourcePosition = 0;

    public SampleVide(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SampleVide(Context context) {
        super(context);
    }

    public SampleVide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        initView();
    }

    private void initView(){
        mMoreScale = (TextView) findViewById(R.id.moreScale);
        mSwitchSize = (TextView) findViewById(R.id.switchSize);
        mChangeRotate = (TextView) findViewById(R.id.change_rotate);
        mChangeTransform = (TextView) findViewById(R.id.change_transform);

        //切换比例
        mMoreScale.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mHadPlay){
                    return;
                }
                if(mType==0){
                    mType = 1;
                }else if(mType==1){
                    mType = 2;
                }else if(mType==2){
                    mType = 3;
                }else if(mType==3){
                    mType = 4;
                } else if (mType==4) {
                    mType = 0;
                }
                resolveTypeUI();
            }
        });

        //切换视频清晰度
        mSwitchSize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSwitchDialog();
            }
        });

        //旋转播放角度
        mChangeRotate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mHadPlay){
                    return;
                }
                if((mTextureView.getRotation()-mRotate)==270){
                    mTextureView.setRotation(mRotate);
                    mTextureView.requestLayout();
                }else{
                    mTextureView.setRotation(mTextureView.getRotation()+90);
                    mTextureView.requestLayout();
                }
            }
        });

        //镜像旋转
        mChangeTransform.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mHadPlay){
                    return;
                }
                if(mTransformSize==0){
                    mTransformSize = 1;
                }else if(mTransformSize==1){
                    mTransformSize = 2;
                }else if(mTransformSize==2){
                    mTransformSize = 0;
                }
                resolveTransform();
            }
        });
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        super.onSurfaceTextureSizeChanged(surface, width, height);
        resolveTransform();
    }

    /**
     * 处理镜像旋转
     * 注意,暂停时
     */
    protected void resolveTransform(){
        Matrix transform = null;
        switch (mTransformSize){
            case 1:
                transform = new Matrix();
                transform.setScale(-1,1,mTextureView.getWidth()/2,0);
                mTextureView.setTransform(transform);
                mChangeTransform.setText("左右镜像");
                mTextureView.invalidate();
                break;
            case 2:
                transform = new Matrix();
                transform.setScale(1,-1,0,mTextureView.getHeight()/2);
                mTextureView.setTransform(transform);
                mChangeTransform.setText("上下镜像");
                mTextureView.invalidate();
                break;
            case 3:
                transform = new Matrix();
                transform.setScale(1,1,mTextureView.getWidth()/2,0);
                mTextureView.setTransform(transform);
                mChangeTransform.setText("旋转镜像");
                mTextureView.invalidate();
                break;
        }
    }

    /**
     * 设置播放URL
     * @param url 播放URL
     * @param cacheWitchPlay    是否边播放边缓存
     * @param title title
     * @return
     */
    public boolean setUp(List<SwitchVideoModel> url,boolean cacheWitchPlay,String title){
        mUrlList = url;
        return setUp(url.get(mSourcePosition).getUrl(),cacheWitchPlay,title);
    }

    /**
     * 设置播放URL
     * @param url 播放URL
     * @param cacheWithPlay 是否边播放边缓存
     * @param cachePath 缓存路径，如果是M3U8或者HLS,请设置false
     * @param title title
     * @return
     */
    public boolean setUp(List<SwitchVideoModel> url, boolean cacheWithPlay, File cachePath, String title){
        mUrlList = url;
        return setUp(url.get(mSourcePosition).getUrl(),cacheWithPlay,cachePath,title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_video;
    }

    /**
     * 全屏时将对应处理参数逻辑赋给全屏播放器
     * @param context
     * @param actionBar
     * @param statusBar
     * @return
     */
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        SampleVide sampleVide = (SampleVide) super.startWindowFullscreen(context,actionBar,statusBar);
        sampleVide.mSourcePosition = mSourcePosition;
        sampleVide.mType = mType;
        sampleVide.mTransformSize = mTransformSize;
        sampleVide.mUrlList = mUrlList;
        sampleVide.resolveTypeUI();
        return sampleVide;
    }

    /**
     * 推出全屏时将对应处理参数逻辑返回给非播放器
     * @param oldF
     * @param vp
     * @param gsyVideoPlayer
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if(gsyVideoPlayer != null){
            SampleVide sampleVide = (SampleVide) gsyVideoPlayer;
            mSourcePosition = sampleVide.mSourcePosition;
            mType = sampleVide.mType;
            mTransformSize = sampleVide.mTransformSize;
            setUp(mUrlList,mCache,mCachePath,mTitle);
            resolveTypeUI();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        super.onSurfaceTextureAvailable(surface, width, height);
        resolveRotateUI();
        resolveTransform();
    }

    /**
     * 旋转逻辑
     */
    private void resolveRotateUI(){
        if (!mHadPlay) {
            return;
        }
        mTextureView.setRotation(mRotate);
        mTextureView.requestLayout();
    }

    /**
     * 显示比例
     */
    private void resolveTypeUI(){
        if(!mHadPlay){
            return;
        }
        if(mType==1){
            mMoreScale.setText("16:9");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);
        }else if(mType==2){
            mMoreScale.setText("4:3");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3);
        }else if(mType==3){
            mMoreScale.setText("全屏");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
        }else if(mType==4){
            mMoreScale.setText("拉伸全屏");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        }else if(mType==0){
            mMoreScale.setText("默认比例");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT);
        }
    }


    private void showSwitchDialog(){
        if(!mHadPlay){
            return;
        }
        SwitchVideoTypeDialog switchVideoTypeDialog = new SwitchVideoTypeDialog(getContext());
        switchVideoTypeDialog.initList(mUrlList, new SwitchVideoTypeDialog.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = mUrlList.get(position).getName();
                if(mSourcePosition!= position){
                    if((mCurrentState == GSYVideoPlayer.CURRENT_STATE_PLAYING||mCurrentState==GSYVideoPlayer.CURRENT_STATE_PAUSE)&& GSYVideoManager.instance().getMediaPlayer()!=null){
                        final String url = mUrlList.get(position).getUrl();
                        onVideoPause();
                        final long currentPosition = mCurrentPosition;
                        GSYVideoManager.instance().releaseMediaPlayer();
                        cancelProgressTimer();
                        hideAllWidget();
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setUp(url,mCache,mCachePath,mTitle);
                                setSeekOnStart(currentPosition);
                                startPlayLogic();
                                cancelProgressTimer();
                                hideAllWidget();
                            }
                        },500);
                        mSwitchSize.setText(name);
                        mSourcePosition = position;
                    }
                }else{
                    ToastUtil.getInstanceFor(getContext()).showShortToast("已经是"+name);
                }
            }
        });
        switchVideoTypeDialog.show();
    }
}
