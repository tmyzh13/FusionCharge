package com.isoftston.issuser.fusioncharge.model.beans;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/27.
 */

public class ChargePileDetailBean {

    private String address;
    private int averageScore;
    private List<GunBean> gunList;
    private int id;
    private double latitude;
    private double longitude;
    private String maxCurrent;
    private String maxPower;
    private String maxVoltage;
    private String name;
    private String objType;
    private String photoUrl;
    private String runCode;
    private int runStatus;
    private int type;
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }
    public int getAverageScore() {
        return averageScore;
    }

    public void setGunList(List<GunBean> gunList) {
        this.gunList = gunList;
    }
    public List<GunBean> getGunList() {
        return gunList;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setMaxCurrent(String maxCurrent) {
        this.maxCurrent = maxCurrent;
    }
    public String getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower = maxPower;
    }
    public String getMaxPower() {
        return maxPower;
    }

    public void setMaxVoltage(String maxVoltage) {
        this.maxVoltage = maxVoltage;
    }
    public String getMaxVoltage() {
        return maxVoltage;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }
    public String getObjType() {
        return objType;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }
    public String getRunCode() {
        return runCode;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }
    public int getRunStatus() {
        return runStatus;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ChargePileDetailBean{" +
                "address='" + address + '\'' +
                ", averageScore=" + averageScore +
                ", gunList=" + gunList +
                ", id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", maxCurrent='" + maxCurrent + '\'' +
                ", maxPower='" + maxPower + '\'' +
                ", maxVoltage='" + maxVoltage + '\'' +
                ", name='" + name + '\'' +
                ", objType='" + objType + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", runCode='" + runCode + '\'' +
                ", runStatus=" + runStatus +
                ", type=" + type +
                '}';
    }
}