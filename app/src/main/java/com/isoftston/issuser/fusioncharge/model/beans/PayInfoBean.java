package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by issuser on 2018/4/25.
 */

public class PayInfoBean {
    public String chargeStartTime;
    public double serviceCharge;//充电服务费
    public double consumeTotalMoney;//消费总金额
    public String chargeEndTime;
    public double chargePowerAmount;//总充电量
    public double eneryCharge;//充电费用
    public long chargeId;//桩
    public long userId;//用户
    public String address;//详细地址
    public String parkAddress;//园区
}
