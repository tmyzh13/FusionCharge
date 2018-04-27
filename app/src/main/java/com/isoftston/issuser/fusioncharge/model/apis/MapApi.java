package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.Condition;
import com.isoftston.issuser.fusioncharge.model.beans.HomeAppointmentBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeChargeOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.PileFeeBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequesHomeMapInfo;
import com.isoftston.issuser.fusioncharge.model.beans.RequestChargeStateBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestFeeBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestHomeAppointment;
import com.isoftston.issuser.fusioncharge.model.beans.RequestMapBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestOnlyUserId;


import java.util.List;

import butterknife.Bind;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by issuser on 2018/4/23.
 */

public interface MapApi {

    @POST(Urls.MAP_DATA)
    Observable<BaseData<List<MapDataBean>>> getMapDatas(@Body Condition bean);

    @POST(Urls.HOME_MAP_INFO)
    Observable<BaseData<MapInfoBean>> getHomeMapInfo(@Body RequesHomeMapInfo info);

    @POST(Urls.QUERY_FEE)
    Observable<BaseData<PileFeeBean>> getFeeData(@Body RequestFeeBean bean);

    @POST(Urls.GET_USER_NOT_PAY)
    Observable<BaseData<HomeOrderBean>> getUserOrderStatue(@Header("AccessToken") String token);

    @POST(Urls.GET_USER_CHARGER_STATUE)
    Observable<BaseData<HomeChargeOrderBean>> getUserChargerStatue(@Header("AccessToken") String token);

    @POST(Urls.GET_CHECK_STATUE)
    Observable<BaseData> getCheckStatue(@Header("AccessToken") String token, @Body RequestChargeStateBean bean);


    @POST(Urls.GET_USER_APPOINTMENT)
    Observable<BaseData<HomeAppointmentBean>> getUserAppointmentRecord(@Header("AccessToken") String token,@Body RequestHomeAppointment bean);
}
