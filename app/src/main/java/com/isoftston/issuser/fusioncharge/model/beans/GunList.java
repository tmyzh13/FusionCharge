package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/27.
 */

/**
 * Copyright 2018 bejson.com
 */

/**
 * Auto-generated: 2018-04-27 22:28:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GunList {

    private int chargingPileId;
    private String currentA;
    private String currentB;
    private String currentC;
    private String gunCode;
    private int gunId;
    private String gunNumber;
    private int gunStatus;
    private int gunType;
    private int isBespeak;
    private String lockBeginTime;
    private String lockUser;
    private String parkingSpacesNumber;
    private String voltageA;
    private String voltageB;
    private String voltageC;
    public void setChargingPileId(int chargingPileId) {
        this.chargingPileId = chargingPileId;
    }
    public int getChargingPileId() {
        return chargingPileId;
    }

    public void setCurrentA(String currentA) {
        this.currentA = currentA;
    }
    public String getCurrentA() {
        return currentA;
    }

    public void setCurrentB(String currentB) {
        this.currentB = currentB;
    }
    public String getCurrentB() {
        return currentB;
    }

    public void setCurrentC(String currentC) {
        this.currentC = currentC;
    }
    public String getCurrentC() {
        return currentC;
    }

    public void setGunCode(String gunCode) {
        this.gunCode = gunCode;
    }
    public String getGunCode() {
        return gunCode;
    }

    public void setGunId(int gunId) {
        this.gunId = gunId;
    }
    public int getGunId() {
        return gunId;
    }

    public void setGunNumber(String gunNumber) {
        this.gunNumber = gunNumber;
    }
    public String getGunNumber() {
        return gunNumber;
    }

    public void setGunStatus(int gunStatus) {
        this.gunStatus = gunStatus;
    }
    public int getGunStatus() {
        return gunStatus;
    }

    public void setGunType(int gunType) {
        this.gunType = gunType;
    }
    public int getGunType() {
        return gunType;
    }

    public void setIsBespeak(int isBespeak) {
        this.isBespeak = isBespeak;
    }
    public int getIsBespeak() {
        return isBespeak;
    }

    public void setLockBeginTime(String lockBeginTime) {
        this.lockBeginTime = lockBeginTime;
    }
    public String getLockBeginTime() {
        return lockBeginTime;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }
    public String getLockUser() {
        return lockUser;
    }

    public void setParkingSpacesNumber(String parkingSpacesNumber) {
        this.parkingSpacesNumber = parkingSpacesNumber;
    }
    public String getParkingSpacesNumber() {
        return parkingSpacesNumber;
    }

    public void setVoltageA(String voltageA) {
        this.voltageA = voltageA;
    }
    public String getVoltageA() {
        return voltageA;
    }

    public void setVoltageB(String voltageB) {
        this.voltageB = voltageB;
    }
    public String getVoltageB() {
        return voltageB;
    }

    public void setVoltageC(String voltageC) {
        this.voltageC = voltageC;
    }
    public String getVoltageC() {
        return voltageC;
    }

    @Override
    public String toString() {
        return "GunList{" +
                "chargingPileId=" + chargingPileId +
                ", currentA='" + currentA + '\'' +
                ", currentB='" + currentB + '\'' +
                ", currentC='" + currentC + '\'' +
                ", gunCode='" + gunCode + '\'' +
                ", gunId=" + gunId +
                ", gunNumber='" + gunNumber + '\'' +
                ", gunStatus=" + gunStatus +
                ", gunType=" + gunType +
                ", isBespeak=" + isBespeak +
                ", lockBeginTime='" + lockBeginTime + '\'' +
                ", lockUser='" + lockUser + '\'' +
                ", parkingSpacesNumber='" + parkingSpacesNumber + '\'' +
                ", voltageA='" + voltageA + '\'' +
                ", voltageB='" + voltageB + '\'' +
                ", voltageC='" + voltageC + '\'' +
                '}';
    }
}