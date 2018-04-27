package com.isoftston.issuser.fusioncharge.model.beans;

import com.corelibs.subscriber.ResponseHandler;

import java.util.List;

/**
 * Created by zhangwei on 2018/4/28.
 */

public class MyOrderData implements ResponseHandler.IBaseData{

    //18672802069

    public int code;

    public String page;

    public String rp;

    public String total;

    public List<RawRecordBean> rawRecords;

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public int status() {
        return 0;
    }

    @Override
    public Object data() {
        return null;
    }

    @Override
    public String msg() {
        return null;
    }

    @Override
    public String toString() {
        return "MyOrderData{" +
                "code=" + code +
                ", page='" + page + '\'' +
                ", rp='" + rp + '\'' +
                ", total='" + total + '\'' +
                ", rawRecords=" + rawRecords +
                '}';
    }
}
