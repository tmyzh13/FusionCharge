package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.ChargeStationDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.OrderRequestInfo;
import com.isoftston.issuser.fusioncharge.model.beans.PileList;
import com.isoftston.issuser.fusioncharge.model.beans.RequestChargePileDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestMyOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestScanBean;
import com.isoftston.issuser.fusioncharge.model.beans.ScanChargeInfo;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhangwei on 2018/4/26.
 */

public interface ScanApi {

    //充电桩信息
    @POST(Urls.SCAN_CHARGE)
    Observable<BaseData<ScanChargeInfo>> getScanChargeInfo(@Header("AccessToken") String token, @Body RequestScanBean bean);

    //开始充电
    @POST(Urls.START_CHARGER)
    Observable<BaseData> getOrderDetail(@Header("AccessToken") String token, @Body OrderRequestInfo bean);

    //充电站详情_STATION
    @POST(Urls.CHARGE_PILE_DETAIL)
    Observable<BaseData<ChargeStationDetailBean>> getChargeStationDetail(@Header("AccessToken") String token, @Body RequestChargePileDetailBean bean);

    //充电站详情_PILE
    @POST(Urls.CHARGE_PILE_DETAIL)
    Observable<BaseData<PileList>> getChargePileDetail(@Header("AccessToken") String token, @Body RequestChargePileDetailBean bean);


    //我的订单
    @POST(Urls.MY_ORDER)
    Observable<BaseData> getMyOrder(@Header("AccessToken") String token, @Body RequestMyOrderBean bean);


}
