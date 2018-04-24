package com.isoftston.issuser.fusioncharge.utils;

import com.isoftston.issuser.fusioncharge.constants.Constant;

/**
 * Created by issuser on 2018/4/20.
 */

public class ChoiceManager {

    public static ChoiceManager instance;

    // 方式空 0，1  01
    private String type;
    // 状态空 0，1 01
    private String statue;
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
        type="";
        statue="";
        distance= Constant.DEFAULT_DISTANCE;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
