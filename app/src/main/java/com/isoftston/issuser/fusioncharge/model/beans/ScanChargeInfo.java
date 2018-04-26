package com.isoftston.issuser.fusioncharge.model.beans;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/26.
 */

public class ScanChargeInfo {

    private String appUserId;
    private List<ChargingGunBeans> chargingGunBeans;
    private int chargingPileId;
    private String chargingPileName;
    private String endTime;
    private double fee;
    private String feeName;
    private String runCode;
    private double serviceFee;
    private String startTime;
    private String virtualId;
    private String stationName;

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public List<ChargingGunBeans> getChargingGunBeans() {
        return chargingGunBeans;
    }

    public void setChargingGunBeans(List<ChargingGunBeans> chargingGunBeans) {
        this.chargingGunBeans = chargingGunBeans;
    }

    public int getChargingPileId() {
        return chargingPileId;
    }

    public void setChargingPileId(int chargingPileId) {
        this.chargingPileId = chargingPileId;
    }

    public String getChargingPileName() {
        return chargingPileName;
    }

    public void setChargingPileName(String chargingPileName) {
        this.chargingPileName = chargingPileName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getRunCode() {
        return runCode;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "ScanChargeInfo{" +
                "appUserId='" + appUserId + '\'' +
                ", chargingGunBeans=" + chargingGunBeans +
                ", chargingPileId=" + chargingPileId +
                ", chargingPileName='" + chargingPileName + '\'' +
                ", endTime='" + endTime + '\'' +
                ", fee=" + fee +
                ", feeName='" + feeName + '\'' +
                ", runCode='" + runCode + '\'' +
                ", serviceFee=" + serviceFee +
                ", startTime='" + startTime + '\'' +
                ", virtualId='" + virtualId + '\'' +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}