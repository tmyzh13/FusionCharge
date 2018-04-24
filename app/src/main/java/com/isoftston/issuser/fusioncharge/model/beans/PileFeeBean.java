package com.isoftston.issuser.fusioncharge.model.beans;

import java.util.List;

/**
 * 充电桩费率
 * Created by issuser on 2018/4/24.
 */

public class PileFeeBean {
    public long chargePileId;
    public List<ChargeFeeBean> feeList;
    public double serviceFee;
    public String multiName;
}
