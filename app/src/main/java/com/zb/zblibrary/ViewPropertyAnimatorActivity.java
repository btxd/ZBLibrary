package com.zb.zblibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zb.library.activity.BaseActivity;
import com.zb.library.animationutil.view.ViewPropertyAnimator;
import com.zb.library.util.ToastUtil;
import com.zb.library.util.ViewUtil;

public class ViewPropertyAnimatorActivity extends BaseActivity implements View.OnClickListener {

    private Button fadeOut;

    private Button fadeIn;

    private Button moveOver;

    private Button moveBack;

    private Button rotate;

    private Button animatingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_animator);
    }

    @Override
    public void initUI() {
        fadeOut = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.fadeOut);
        fadeIn = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.fadeIn);
        moveOver = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.moveOver);
        moveBack = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.moveBack);
        rotate = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.rotate);
        animatingButton = ViewUtil.getInstanceFor(zbActivity).findWindow(R.id.animatingButton);
    }

    @Override
    public void disposeLogic() {

    }

    @Override
    public void initOnClick() {
        fadeOut.setOnClickListener(this);
        fadeIn.setOnClickListener(this);
        moveOver.setOnClickListener(this);
        moveBack.setOnClickListener(this);
        rotate.setOnClickListener(this);
        animatingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fadeOut:
                ViewPropertyAnimator.animate(animatingButton).alpha(0).setDuration(5000);
                break;
            case R.id.fadeIn:
                ViewPropertyAnimator.animate(animatingButton).alpha(1).setDuration(5000);
                break;
            case R.id.moveOver:
                ViewPropertyAnimator.animate(animatingButton).x(100).y(100).setDuration(5000);
                break;
            case R.id.moveBack:
                ViewPropertyAnimator.animate(animatingButton).x(0).y(0).setDuration(5000);
                break;
            case R.id.rotate:
                ViewPropertyAnimator.animate(animatingButton).rotationYBy(720);
                break;
            case R.id.animatingButton:
                ToastUtil.getInstanceFor(zbActivity).showShortToast("animatingButton");
                break;
            default:

                break;
        }
    }
}
