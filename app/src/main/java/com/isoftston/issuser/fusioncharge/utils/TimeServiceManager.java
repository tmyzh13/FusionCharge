package com.isoftston.issuser.fusioncharge.utils;

import com.isoftston.issuser.fusioncharge.views.TimerService;

/**
 * Created by issuser on 2018/4/25.
 */

public class TimeServiceManager {

    private TimerService timerService;

    private static TimeServiceManager instance;

    private TimeServiceManager(){

    }

    public static TimeServiceManager getInstance(){
        if(instance==null){
            instance=new TimeServiceManager();
        }
        return instance;
    }

    private void setTimerService(TimerService timerService){
        this.timerService=timerService;
    }
}
