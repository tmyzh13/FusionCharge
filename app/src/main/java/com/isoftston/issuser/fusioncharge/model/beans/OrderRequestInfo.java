package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/26.
 */

public class OrderRequestInfo {

    private long chargingPileId;
    private String chargingPileName;
    private String virtualId;
    private String appUserId;
    private String gunCode;
    private String chargingMode;
    private String controlInfo;
    private String controlData;
    private String orderRecordNum;

    public long getChargingPileId() {
        return chargingPileId;
    }

    public void setChargingPileId(long chargingPileId) {
        this.chargingPileId = chargingPileId;
    }

    public String getChargingPileName() {
        return chargingPileName;
    }

    public void setChargingPileName(String chargingPileName) {
        this.chargingPileName = chargingPileName;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }

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

    public String getChargingMode() {
        return chargingMode;
    }

    public void setChargingMode(String chargingMode) {
        this.chargingMode = chargingMode;
    }

    public String getControlInfo() {
        return controlInfo;
    }

    public void setControlInfo(String controlInfo) {
        this.controlInfo = controlInfo;
    }

    public String getControlData() {
        return controlData;
    }

    public void setControlData(String controlData) {
        this.controlData = controlData;
    }

    public String getOrderRecordNum() {
        return orderRecordNum;
    }

    public void setOrderRecordNum(String orderRecordNum) {
        this.orderRecordNum = orderRecordNum;
    }

    @Override
    public String toString() {
        return "OrderRequestInfo{" +
                "chargingPileId=" + chargingPileId +
                ", chargingPileName='" + chargingPileName + '\'' +
                ", virtualId='" + virtualId + '\'' +
                ", appUserId=" + appUserId +
                ", gunCode='" + gunCode + '\'' +
                ", chargingMode='" + chargingMode + '\'' +
                ", controlInfo='" + controlInfo + '\'' +
                ", controlData='" + controlData + '\'' +
                ", orderRecordNum='" + orderRecordNum + '\'' +
                '}';
    }
}
