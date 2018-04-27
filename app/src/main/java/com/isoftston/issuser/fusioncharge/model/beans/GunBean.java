package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/27.
 */

public class GunBean {

    private String gunCode;
    private int gunStatus;
    private int gunType;
    private int isBespeak;
    public void setGunCode(String gunCode) {
        this.gunCode = gunCode;
    }
    public String getGunCode() {
        return gunCode;
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

    @Override
    public String toString() {
        return "GunBean{" +
                "gunCode='" + gunCode + '\'' +
                ", gunStatus=" + gunStatus +
                ", gunType=" + gunType +
                ", isBespeak=" + isBespeak +
                '}';
    }
}