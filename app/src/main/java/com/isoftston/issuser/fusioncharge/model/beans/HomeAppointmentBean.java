package com.isoftston.issuser.fusioncharge.model.beans;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/24.
 */

public class HomeAppointmentBean implements Serializable {

    public long appUserId;
    public String chargingAddress;
    public long chargingPileId;
    public String chargingPileName;
    public String gunCode;
    public long nowTime;//当前时间
    public long reserveBeginTime;//
    public long reserveDuration;
    public long reserveEndTime;
    public String runCode;//充电桩编码
    public double latitude;
    public double longitude;
    public long reserveId;
}
