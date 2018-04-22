package com.isoftston.issuser.fusioncharge.views;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.constants.Constant;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by issuser on 2018/4/22.
 */

public class TimerService extends Service{

    private ServiceBinder mBinder=new ServiceBinder();
    private Timer timer;
    private Timer timerSec;
    private boolean isStartCharge=false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer=new Timer();
        timerSec=new Timer();
    }

    private Long hourTime;

    //更新充电时间
    public void timerHour(){
        //一分钟更新一次时间
        if(isStartCharge){
            //已经开始了不用再执行一个操作
            return;
        }else{
            isStartCharge=true;
        }
        if(timer==null){
            timer=new Timer();
        }
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                hourTime=Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME));
//                Log.e("yzh","timerHour----"+PreferencesHelper.getData(Constant.CHARGING_TIME));
//                hourTime-=60*1000;
//                PreferencesHelper.saveData(Constant.CHARGING_TIME,hourTime+"");
//                RxBus.getDefault().send(new Object(),Constant.CHARGING_TIME);
//                if(hourTime<=0){
//                    Log.e("yzh","cancel");
//                    cancelTimerHour();
//                }
//            }
//        },5*1000,5*1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hourTime=Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME));
                Log.e("yzh","timerHour----"+PreferencesHelper.getData(Constant.CHARGING_TIME));
                hourTime+=1000;
                PreferencesHelper.saveData(Constant.CHARGING_TIME,hourTime+"");
                if(hourTime%(60*1000)==0){
                    //是整分钟 去刷新时间

                    RxBus.getDefault().send(new Object(),Constant.CHARGING_TIME);
                }
                if(hourTime>=Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TOTAL))){
                    Log.e("yzh","cancel");
                    cancelTimerHour();
                }
            }
        },1000,1000);
    }

    public void cancelTimerHour(){
        if(timer!=null){
            timer.cancel();
            timer=null;
            isStartCharge=false;
        }
    }

    public class ServiceBinder extends Binder{
        TimerService getService(){
            return TimerService.this;
        }
    }
}
