package com.isoftston.issuser.fusioncharge.utils;

/**
 * Created by issuser on 2018/4/20.
 */

public class ChoiceManager {

    public static ChoiceManager instance;

    // 方式空 0，1  01
    private String type;
    // 状态空 0，1 01
    private String statue;
    //距离
    private String distance;

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
        distance="";
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
