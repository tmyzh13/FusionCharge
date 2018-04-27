package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/27.
 */

public class GunList {

    private int chargingPileId;
    private String gunCode;
    private int gunId;
    private int gunStatus;
    private int gunType;
    private int isBespeak;
    private String parkingSpacesNumber;
    public void setChargingPileId(int chargingPileId) {
        this.chargingPileId = chargingPileId;
    }
    public int getChargingPileId() {
        return chargingPileId;
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

    public void setParkingSpacesNumber(String parkingSpacesNumber) {
        this.parkingSpacesNumber = parkingSpacesNumber;
    }
    public String getParkingSpacesNumber() {
        return parkingSpacesNumber;
    }

    @Override
    public String toString() {
        return "GunList{" +
                "chargingPileId=" + chargingPileId +
                ", gunCode='" + gunCode + '\'' +
                ", gunId=" + gunId +
                ", gunStatus=" + gunStatus +
                ", gunType=" + gunType +
                ", isBespeak=" + isBespeak +
                ", parkingSpacesNumber='" + parkingSpacesNumber + '\'' +
                '}';
    }
}