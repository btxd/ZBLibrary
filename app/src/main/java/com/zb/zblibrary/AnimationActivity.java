package com.zb.zblibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zb.library.activity.BaseActivity;
import com.zb.library.animationutil.animation.ObjectAnimator;
import com.zb.library.animationutil.view.ViewPropertyAnimator;
import com.zb.library.util.ToastUtil;
import com.zb.library.util.ViewUtil;

public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    private Button tx;

    private Button target;

    private Button ty;

    private Button sx;

    private Button xy;

    private Button a;

    private Button rx;

    private Button ry;

    private Button rz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    @Override
    public void initUI() {
        tx = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.tx);
        target = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.target);
        ty = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.ty);
        sx = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.sx);
        xy = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.xy);
        a = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.a);
        rx = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.rx);
        ry = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.ry);
        rz = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.rz);
    }

    @Override
    public void disposeLogic() {

    }

    @Override
    public void initOnClick() {
        tx.setOnClickListener(this);
        target.setOnClickListener(this);
        ty.setOnClickListener(this);
        sx.setOnClickListener(this);
        xy.setOnClickListener(this);
        a.setOnClickListener(this);
        rx.setOnClickListener(this);
        ry.setOnClickListener(this);
        rz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx:
                ObjectAnimator.ofFloat(target,"translationX",0,50,-50,0).setDuration(2000).start();
                break;
            case R.id.target:
                ToastUtil.getInstanceFor(zbActivity).showShortToast("针对控件");
                break;
            case R.id.ty:
                ObjectAnimator.ofFloat(target,"translationY",0,50,-50,0).setDuration(2000).start();
                break;
            case R.id.sx:
                ObjectAnimator.ofFloat(target,"scaleX",1,2,1).setDuration(2000).start();
                break;
            case R.id.xy:
                ObjectAnimator.ofFloat(target,"scaleY",1,2,1).setDuration(2000).start();
                break;
            case R.id.a:
                ObjectAnimator.ofFloat(target,"alpha",1,0,1).setDuration(2000).start();
                break;
            case R.id.rx:
                ObjectAnimator.ofFloat(target,"rotationX",0,180,0).setDuration(2000).start();
                break;
            case R.id.ry:
                ObjectAnimator.ofFloat(target,"rotationY",0,180,0).setDuration(2000).start();
                break;
            case R.id.rz:
                ObjectAnimator.ofFloat(target,"rotation",0,180,0).setDuration(2000).start();
                break;
            default:

                break;
        }
    }
}
