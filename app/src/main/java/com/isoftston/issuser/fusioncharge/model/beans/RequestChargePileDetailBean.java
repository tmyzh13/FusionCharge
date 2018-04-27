package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/27.
 */

public class RequestChargePileDetailBean {

    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RequestChargePileDetailBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
