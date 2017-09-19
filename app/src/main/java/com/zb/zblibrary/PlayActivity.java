package com.zb.zblibrary;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zb.library.activity.BaseActivity;
import com.zb.library.util.ViewUtil;
import com.zb.zblibrary.model.SwitchVideoModel;
import com.zb.zblibrary.video.SampleVide;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends BaseActivity {

    private final static String IMG_TRANSITION = "IMG_TRANSITION";

    private final static String TRANSITION = "TRANSITION";

    private SampleVide sampleVide;

    private boolean isTransition;

    private Transition transition;

    private OrientationUtils orientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    @Override
    public void initUI() {
        sampleVide = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.video_player);
    }

    @Override
    public void disposeLogic() {

        isTransition = true;

        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";

        String sourcel = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

        String name = "普通";

        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name,sourcel);

        String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";

        String name2 = "清晰";

        SwitchVideoModel switchVideoModel1 = new SwitchVideoModel(name2,source2);

        List<SwitchVideoModel> list = new ArrayList<>();
        list.add(switchVideoModel);
        list.add(switchVideoModel1);

        sampleVide.setUp(list,true,"测试视频");

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx1);
        sampleVide.setThumbImageView(imageView);

        sampleVide.getTitleTextView().setVisibility(View.VISIBLE);

        sampleVide.getBackButton().setVisibility(View.VISIBLE);

        orientationUtils = new OrientationUtils(this,sampleVide);

        sampleVide.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });

        sampleVide.setIsTouchWiget(true);

        sampleVide.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initTransition();

    }

    @Override
    public void initOnClick() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sampleVide.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(orientationUtils != null){
            orientationUtils.releaseListener();
        }
    }

    @Override
    public void onBackPressed() {
        if(orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            sampleVide.getFullscreenButton().performClick();
            return;
        }
        sampleVide.setStandardVideoAllCallBack(null);
        GSYVideoPlayer.releaseAllVideos();
        if(isTransition&& Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            super.onBackPressed();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },500);
        }
    }

    private void initTransition(){
        if (isTransition&&Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(sampleVide,IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        }else{
            sampleVide.startPlayLogic();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener(){
        transition = getWindow().getSharedElementEnterTransition();
        if(transition!=null){
            transition.addListener(new Transition.TransitionListener(){

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    sampleVide.startPlayLogic();
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
            return true;
        }
        return false;
    }
}
