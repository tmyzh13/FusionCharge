package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by issuser on 2018/4/19.
 */

public class LoginRequestBean {

    public String phone;
    public String carrier;//手机类型android iphone
    public String passWord;
    public String captcha;//验证码
    public int type;//0:密码登录 1:验证码登录
}
