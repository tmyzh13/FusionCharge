package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.RequstChargeStatueBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/24.
 */

public interface ChargeStatueApi {

    @POST(Urls.GET_CHARGE_STATUE)
    Observable<BaseData> getChargeStatue(@Body RequstChargeStatueBean bean);
}
