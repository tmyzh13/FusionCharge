package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.PayInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestPayBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestPayDetailBean;


import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/25.
 */

public interface PayApi {

    @POST(Urls.GET_PAY_DETAIL)
    Observable<BaseData<PayInfoBean>> getPayDetail(@Header("AccessToken") String token, @Body RequestPayDetailBean bean);

    @POST(Urls.BALANCE_PAY)
    Observable<BaseData> balancePay(@Header("AccessToken") String token,
                                    @Body RequestPayBean bean);
}
