package com.isoftston.issuser.fusioncharge.model.beans;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/26.
 */

public class HomeChargeOrderBean implements Serializable{

    public String address;
    public String orderRecordNum;
    public long chargeId;
    public String chargeGunNum; //gunCode
    public long id;
    public String virtualId;
    public long userId;

}
