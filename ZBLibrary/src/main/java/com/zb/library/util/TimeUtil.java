package com.zb.library.util;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * 类名：TimeUtil
 * 作者： 冰
 * 创建日期： 2017/8/31
 * 描述：时间操作类
 *
 **/
public class TimeUtil {

    private static volatile TimeUtil timeUtil;

    private TimerHandler timerHandler;

    private Timer timer;

    private TimeUtil(){}

    public static TimeUtil getInstanceFor(){
        if(null == timeUtil){
            synchronized (TimeUtil.class){
                if(timeUtil==null){
                    timeUtil = new TimeUtil();
                }
            }
        }
        return timeUtil;
    }

    /**
     * 业务简单定时操作,开始定时
     * 冰
     * @param duration 间隔时间 ，毫秒
     * @param onOperationDispose 业务处理
     */
    public void startEasyTimer(int duration, OnEasyOperationDispose onOperationDispose){
        timerHandler = new TimerHandler(duration,onOperationDispose);
        timerHandler.sendEmptyMessage(1);
    }

    /**
     * 业务简单定时操作，停止定时
     * 冰
     */
    public void stopEasyTimer(){
        if(timerHandler==null){
            throw new NullPointerException("对象不能为空");
        }
        timerHandler.sendEmptyMessage(0);
        timerHandler = null;
    }

    /**
     * 业务复杂定时操作，开始定时
     * @param delay 延迟时间，0为立即执行
     * @param duration 间隔时间
     * @param onComplexOperationDispose 业务处理回调
     */
    public void startComplexTimer(int delay,int duration,final OnComplexOperationDispose onComplexOperationDispose){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onComplexOperationDispose.complexOperationDispose();
            }
        },delay,duration);
    }

    /**
     * 业务复杂定时操作，停止定时
     * 冰
     */
    public void stopComplexTimer(){
        if(timer==null){
            throw new NullPointerException("对象不能为空");
        }
        timer.cancel();
        timer = null;
    }

    /**
     * 定时器
     * 简单UI操作，不宜复杂业务
     */
    private static class TimerHandler extends Handler{
        //时间间隔,毫秒
        private int duration;

        //业务处理
        private OnEasyOperationDispose onOperationDispose;

        public TimerHandler(int duration,OnEasyOperationDispose onOperationDispose){
            this.duration = duration;
            this.onOperationDispose = onOperationDispose;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //移除
                    this.removeMessages(1);
                    onOperationDispose.easyOperationDispose();
                    //循环
                    this.sendEmptyMessageDelayed(1,duration);
                    break;
                case 0:
                    this.removeMessages(1);
                    break;
            }
        }


    }

    public interface OnComplexOperationDispose{
        void complexOperationDispose();
    }

    public interface OnEasyOperationDispose{
        void easyOperationDispose();
    }
}
