package com.isoftston.issuser.fusioncharge.model.beans;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/27.
 */


public class PileList {

    private List<GunList> gunList;
    private int id;
    private String maxCurrent;
    private String maxPower;
    private String maxVoltage;
    private String name;
    private String runCode;
    private int runStatus;
    public void setGunList(List<GunList> gunList) {
        this.gunList = gunList;
    }
    public List<GunList> getGunList() {
        return gunList;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "PileList{" +
                "gunList=" + gunList +
                ", id=" + id +
                ", maxCurrent='" + maxCurrent + '\'' +
                ", maxPower='" + maxPower + '\'' +
                ", maxVoltage='" + maxVoltage + '\'' +
                ", name='" + name + '\'' +
                ", runCode='" + runCode + '\'' +
                ", runStatus=" + runStatus +
                '}';
    }
}