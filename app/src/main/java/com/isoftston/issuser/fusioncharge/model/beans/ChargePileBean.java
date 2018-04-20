package com.isoftston.issuser.fusioncharge.model.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 充电桩 + 枪
 * Created by issuser on 2018/4/19.
 */

public class ChargePileBean implements Serializable {
    private String pileNum;
    private String maxPower;
    private String maxElectric;
    private String maxVoltage;
    private List<ElectricGunBean> gunBeanList;

    public List<ElectricGunBean> getGunBeanList() {
        return gunBeanList;
    }

    public void setGunBeanList(List<ElectricGunBean> gunBeanList) {
        this.gunBeanList = gunBeanList;
    }

    public String getPileNum() {
        return pileNum;
    }

    public void setPileNum(String pileNum) {
        this.pileNum = pileNum;
    }

    public String getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower = maxPower;
    }

    public String getMaxElectric() {
        return maxElectric;
    }

    public void setMaxElectric(String maxElectric) {
        this.maxElectric = maxElectric;
    }

    public String getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(String maxVoltage) {
        this.maxVoltage = maxVoltage;
    }
    public static class ElectricGunBean{
        public String gunNum;
        public String type;//交流、直流、、、
        public String status;//是否可预约

        public String getGunNum() {
            return gunNum;
        }

        public void setGunNum(String gunNum) {
            this.gunNum = gunNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
