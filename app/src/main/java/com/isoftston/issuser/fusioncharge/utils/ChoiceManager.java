package com.isoftston.issuser.fusioncharge.utils;

import com.isoftston.issuser.fusioncharge.constants.Constant;

/**
 * Created by issuser on 2018/4/20.
 */

public class ChoiceManager {

    public static ChoiceManager instance;

    // 方式空 1交流2直流3交直流一体
    private int type=0;
    // 状态空 //充电方式1快充2慢充3快慢充
    private int statue=0;
    //距离 默认距离是100 这里测试改成500
    private double distance=Constant.DEFAULT_DISTANCE;

    private ChoiceManager(){

    }

    public static ChoiceManager getInstance(){
        if(instance==null){
            instance=new ChoiceManager();
        }
        return instance;
    }

    public void resetChoice(){
        type=0;
        statue=0;
        distance= Constant.DEFAULT_DISTANCE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
