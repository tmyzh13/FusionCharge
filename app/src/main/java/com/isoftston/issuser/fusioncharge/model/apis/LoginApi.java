package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.UserBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/19.
 */

public interface LoginApi {
    //login
    @POST(Urls.LOGIN)
    Observable<BaseData<UserBean>> login(@Body LoginRequestBean bean);

    @POST(Urls.REGISTER)
    Observable<BaseData> register(@Body LoginRequestBean bean);

    @POST(Urls.GET_CODE)
    Observable<BaseData> getCode(@Body LoginRequestBean bean);
}
