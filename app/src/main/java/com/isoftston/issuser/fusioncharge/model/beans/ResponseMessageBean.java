package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by issuser on 2018/4/27.
 */

public class ResponseMessageBean {
    public int code;//返回码
    public UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
