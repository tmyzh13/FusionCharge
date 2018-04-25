package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.RequestCancelAppointment;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by issuser on 2018/4/25.
 */

public interface GuaildApi {
    @POST(Urls.CANCEL_APPOINTMENT)
    Observable<BaseData> cancelAppointment(@Body RequestCancelAppointment bean);
}
