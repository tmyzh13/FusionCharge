package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.ModifyPwdRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.ResponseMessageBean;
import com.isoftston.issuser.fusioncharge.model.beans.RestPwdRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.UserBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
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

    //重置密码、忘记密码
    @POST(Urls.RESET_PWD)
    Observable<BaseData> restPwd(@Body RestPwdRequestBean bean);

    //修改密码
    @POST(Urls.RESET_PWD)
    Observable<BaseData<ResponseMessageBean>> modifyPwd(@Header("AccessToken")String accessToken,@Body ModifyPwdRequestBean bean);
}
