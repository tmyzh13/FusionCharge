package com.isoftston.issuser.fusioncharge.constants;

/**
 * 接口地址
 * Created by john on 2017/11/7.
 */

public class Urls {


    public static final String ROOT="http://10.40.143.17:8089/charger/api/v1/";

    //登录
    public static final String LOGIN = "user/login";
    //注册
    public static final String REGISTER = "user/register";
    public static final String GET_CODE = "user/captcha";
    //预约
    public static final String APPOINT_CHARGE = "user/login";

    //首页地图和列表
    public static final String MAP_DATA="appMap/search";
    //首页标记简介
    public static final String HOME_MAP_INFO="appMap/infoNew";
    //查询充电桩费率
    public static final String QUERY_FEE="appMap/queryFee";

    //查询用户是否有未支付的订单
    public static final String GET_USER_NOT_PAY="http://10.40.143.130:8088/charger/api/v1/appChargeOrder/queryAppChargeOrderNoPay";
    //查询用户充电状态
    public static final String GET_USER_CHARGER_STATUE="http://10.40.143.130:8088/charger/api/v1/appChargeOrder/queryAppChargeOrderCharging";
    //获取充电详情
    public static final String GET_CHARGE_STATUE="charging/queryChargingState";
    //获取用户预约记录
    public static final String GET_USER_APPOINTMENT="charging/getReserveById";
}
