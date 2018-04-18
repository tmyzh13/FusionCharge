package com.isoftston.issuser.fusioncharge.constants;

/**
 * 接口地址
 * Created by john on 2017/11/7.
 */

public class Urls {


    public static final String ROOT="http://10.28.124.188:8090/conch/";

    //登录
    public static final String LOGIN = "";
    //忘记密码
    public static final String FORGET_PASSWORD="";
    //获取验证码
    public static final String GET_CODE="";
    //获取所有设备
    public static final String GET_DEVICES = "device/currentExamine";
    //查询设备说明信息
    public static final String CHECK_DEVICE_DESCRIPTION="device/searchDescription";
    //扫面之后根据信息来获取设备信息
    public static final String CHECK_DEVICE="device/currentExamine";
    //查询安全信息列表数据
    public static final String GET_CONCH_NEWS="/info/getConchNews";

    //获取安全信息列表（隐患违章我的）
    public static final String GET_SECURITY_DATAS="info/conditionsearch";
    //作业详细信息查询
    public static final String GET_WORK_DETAIL_INFO="job/getJobDetail";
    //撤销作业
    public static final String CANCEL_JOB="job/cancelJob";
    //提交作业
    public static final String SUBMIT_JOB="job/submitlJob";
    //查询当前用户信息
    public static final String GET_MY_INFO="my/getMyInfo";
}
