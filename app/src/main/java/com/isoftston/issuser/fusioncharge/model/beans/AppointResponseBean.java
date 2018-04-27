package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/27.
 */

public class AppointResponseBean {

    private int appUserId;
    private int chargingPileId;
    private String chargingPileName;
    private long createTime;
    private String gunCode;
    private long reserveBeginTime;
    private int reserveDuration;
    private long reserveEndTime;
    private int reserveId;
    private int state;


    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
    public int getAppUserId() {
        return appUserId;
    }

    public void setChargingPileId(int chargingPileId) {
        this.chargingPileId = chargingPileId;
    }
    public int getChargingPileId() {
        return chargingPileId;
    }

    public void setChargingPileName(String chargingPileName) {
        this.chargingPileName = chargingPileName;
    }
    public String getChargingPileName() {
        return chargingPileName;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public long getCreateTime() {
        return createTime;
    }

    public void setGunCode(String gunCode) {
        this.gunCode = gunCode;
    }
    public String getGunCode() {
        return gunCode;
    }

    public void setReserveBeginTime(long reserveBeginTime) {
        this.reserveBeginTime = reserveBeginTime;
    }
    public long getReserveBeginTime() {
        return reserveBeginTime;
    }

    public void setReserveDuration(int reserveDuration) {
        this.reserveDuration = reserveDuration;
    }
    public int getReserveDuration() {
        return reserveDuration;
    }

    public void setReserveEndTime(long reserveEndTime) {
        this.reserveEndTime = reserveEndTime;
    }
    public long getReserveEndTime() {
        return reserveEndTime;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }
    public int getReserveId() {
        return reserveId;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return "AppointResponceBean{" +
                "appUserId=" + appUserId +
                ", chargingPileId=" + chargingPileId +
                ", chargingPileName='" + chargingPileName + '\'' +
                ", createTime=" + createTime +
                ", gunCode='" + gunCode + '\'' +
                ", reserveBeginTime=" + reserveBeginTime +
                ", reserveDuration=" + reserveDuration +
                ", reserveEndTime=" + reserveEndTime +
                ", reserveId=" + reserveId +
                ", state=" + state +
                '}';
    }
}
