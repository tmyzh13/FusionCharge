package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/28.
 */

public class RawRecordBean {

    private String address;
    private int appUserId;
    private int chargePowerAmount;
    private String chargingTime;
    private int eneryCharge;
    private String gunCode;
    private int id;
    private String orderNum;
    private int payStatus;
    private String runCode;
    private int serviceCharge;
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
    public int getAppUserId() {
        return appUserId;
    }

    public void setChargePowerAmount(int chargePowerAmount) {
        this.chargePowerAmount = chargePowerAmount;
    }
    public int getChargePowerAmount() {
        return chargePowerAmount;
    }

    public void setChargingTime(String chargingTime) {
        this.chargingTime = chargingTime;
    }
    public String getChargingTime() {
        return chargingTime;
    }

    public void setEneryCharge(int eneryCharge) {
        this.eneryCharge = eneryCharge;
    }
    public int getEneryCharge() {
        return eneryCharge;
    }

    public void setGunCode(String gunCode) {
        this.gunCode = gunCode;
    }
    public String getGunCode() {
        return gunCode;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    public String getOrderNum() {
        return orderNum;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }
    public int getPayStatus() {
        return payStatus;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }
    public String getRunCode() {
        return runCode;
    }

    public void setServiceCharge(int serviceCharge) {
        this.serviceCharge = serviceCharge;
    }
    public int getServiceCharge() {
        return serviceCharge;
    }

    @Override
    public String toString() {
        return "RawRecordBean{" +
                "address='" + address + '\'' +
                ", appUserId=" + appUserId +
                ", chargePowerAmount=" + chargePowerAmount +
                ", chargingTime='" + chargingTime + '\'' +
                ", eneryCharge=" + eneryCharge +
                ", gunCode='" + gunCode + '\'' +
                ", id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", payStatus=" + payStatus +
                ", runCode='" + runCode + '\'' +
                ", serviceCharge=" + serviceCharge +
                '}';
    }
}