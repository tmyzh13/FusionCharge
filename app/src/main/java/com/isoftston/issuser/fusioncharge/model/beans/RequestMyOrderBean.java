package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by zhangwei on 2018/4/28.
 */

public class RequestMyOrderBean {

    private String page;

    private String rp; //每页请求数据量

    private RequestMyOrderChildBean condition;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    public RequestMyOrderChildBean getCondition() {
        return condition;
    }

    public void setCondition(RequestMyOrderChildBean condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "RequestMyOrderBean{" +
                "page='" + page + '\'' +
                ", rp='" + rp + '\'' +
                ", condition=" + condition +
                '}';
    }
}
