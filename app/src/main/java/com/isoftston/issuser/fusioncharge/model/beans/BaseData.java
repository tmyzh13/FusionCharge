package com.isoftston.issuser.fusioncharge.model.beans;

import com.corelibs.subscriber.ResponseHandler;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/9.
 */

public class BaseData<T> implements Serializable,ResponseHandler.IBaseData {

    public int code;
    public String msg;
    public T data;
    public Page page;

    @Override
    public boolean isSuccess() {
        return code==1;
    }

    @Override
    public int status() {
        return code;
    }

    @Override
    public Object data() {
        return data;
    }

    @Override
    public String msg() {
        return msg;
    }
}
