package com.isoftston.issuser.fusioncharge.constants;

/**
 * 接口地址
 * Created by john on 2017/11/7.
 */

public class Urls {


    public static final String ROOT="http://10.40.143.17:8088/charger/api/v1/";//菊
//    public static final String ROOT="http://10.40.143.67:8088/charger/api/v1/";//李凯
//    public static final String ROOT="http://10.40.143.130:8088/charger/api/v1/";//胡明明
//    public static final String ROOT="http://10.40.143.130:8088/charger/api/v1/";//菊1


    //登录
    public static final String LOGIN = "user/login";
    //注册
    public static final String REGISTER = "user/register";
    //忘记密码
    public static final String RESET_PWD = "user/restpwd";
    //修改密码
    public static final String MODIFY_PWD = "user/modifypwd";
    //获取验证码
    public static final String GET_CODE = "user/captcha";
    //预约
    public static final String APPOINT_CHARGE = "charging/reserve";

    //首页地图和列表
    public static final String MAP_DATA="appMap/search";
    //首页标记简介
    public static final String HOME_MAP_INFO="appMap/infoNew";
    //查询充电桩费率
    public static final String QUERY_FEE="appMap/queryFee";

    //查询用户是否有未支付的订单
    public static final String GET_USER_NOT_PAY="appChargeOrder/queryAppChargeOrderNoPay";
    //查询用户充电状态
    public static final String GET_USER_CHARGER_STATUE="appChargeOrder/queryAppChargeOrderCharging";
    //获取充电详情
    public static final String GET_CHARGE_STATUE="charging/queryChargingState";
    //获取用户预约记录
    public static final String GET_USER_APPOINTMENT="charging/getReserveById";
    //获取充电检查状态（（0失败1成功2检测中））
    public static final String GET_CHECK_STATUE="charging/queryChargingState";
    //获取支付页面详情
    public static final String GET_PAY_DETAIL="appChargeOrder/queryAppChargeOrderDetail";
    //取消预约
    public static final String CANCEL_APPOINTMENT="charging/cancelReserve";
    //余额支付
    public static final String BALANCE_PAY="orderPay/charge";
    //结束充电
    public static final String STOP_CHARGER="charging/stop";
    //开始充电
    public static final String START_CHARGER="charging/start";

    //扫码充电
    public static final String SCAN_CHARGE = "charging/scan";
    //发布评论
    public static final String COMMENT="appChargingPileEvaluate/addAppChargingPileEvaluate";
    public static final String COMMENT_SORT="appChargingPileEvaluate/queryAppEvaluateTypeByStation";
    public static final String COMMENT_INFO="appChargingPileEvaluate/queryAppChargingPileEvaluateByStation";
    public static final String COMMENT_INFO_TYPE="appChargingPileEvaluate/queryAppEvaluateType";

    //充电站信息
    public static final String CHARGE_PILE_DETAIL="appMap/info";

    //我的订单
    public static final String MY_ORDER="user/queryAllChargeOrder";
}
