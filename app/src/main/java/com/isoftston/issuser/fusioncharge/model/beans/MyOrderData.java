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
        return code == 0;
    }

    @Override
    public int status() {
        return code;
    }

    @Override
    public Object data() {
        return rawRecords;
    }

    @Override
    public String msg() {
        return null;
    }

    public String page(){
        return page;
    }

    public String rp(){
        return rp;
    }

    public String total(){
        return total;
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
