package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.AppointRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/23.
 */

public interface AppointApi {
    @POST(Urls.LOGIN)
    Observable<BaseData> appoint(@Body AppointRequestBean bean);
}
