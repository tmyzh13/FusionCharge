package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.AppointRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.AppointResponseBean;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/23.
 */

public interface AppointApi {
    @POST(Urls.APPOINT_CHARGE)
    Observable<BaseData<AppointResponseBean>> appoint(@Header("AccessToken") String token, @Body AppointRequestBean bean);
}
