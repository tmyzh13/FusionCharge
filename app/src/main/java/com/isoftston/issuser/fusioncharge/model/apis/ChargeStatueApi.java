package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.ChargerStatueBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestEndChargerBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestStartChargerBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequstChargeStatueBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/24.
 */

public interface ChargeStatueApi {

    @POST(Urls.GET_CHARGE_STATUE)
    Observable<BaseData<ChargerStatueBean>> getChargeStatue(@Header("AccessToken") String token,@Body RequstChargeStatueBean bean);

    @POST(Urls.START_CHARGER)
    Observable<BaseData> startCharge(@Body RequestStartChargerBean bean);

    @POST(Urls.STOP_CHARGER)
    Observable<BaseData> stopCharge(@Body RequestEndChargerBean bean);
}
