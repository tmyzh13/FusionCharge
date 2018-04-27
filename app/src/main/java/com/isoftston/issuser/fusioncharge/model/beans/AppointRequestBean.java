package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by issuser on 2018/4/23.
 *
 * modify by zhangwei on 2018/4/27
 */

public class AppointRequestBean {
    private String appUserId;//APP用户ID
    private String gunCode;//枪编号
    private String chargingPileId;//充电桩ID

    private String chargingPileName;
    private String reserveBeginTime;
    private String reserveEndTime;
    private String reserveDuration;

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getGunCode() {
        return gunCode;
    }

    public void setGunCode(String gunCode) {
        this.gunCode = gunCode;
    }

    public String getChargingPileId() {
        return chargingPileId;
    }

    public void setChargingPileId(String chargingPileId) {
        this.chargingPileId = chargingPileId;
    }

    public String getChargingPileName() {
        return chargingPileName;
    }

    public void setChargingPileName(String chargingPileName) {
        this.chargingPileName = chargingPileName;
    }

    public String getReserveBeginTime() {
        return reserveBeginTime;
    }

    public void setReserveBeginTime(String reserveBeginTime) {
        this.reserveBeginTime = reserveBeginTime;
    }

    public String getReserveEndTime() {
        return reserveEndTime;
    }

    public void setReserveEndTime(String reserveEndTime) {
        this.reserveEndTime = reserveEndTime;
    }

    public String getReserveDuration() {
        return reserveDuration;
    }

    public void setReserveDuration(String reserveDuration) {
        this.reserveDuration = reserveDuration;
    }
}
