package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.OrderRequestInfo;
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

    @POST(Urls.SCAN_CHARGE)
    Observable<BaseData<ScanChargeInfo>> getScanChargeInfo(@Header("AccessToken") String token, @Body RequestScanBean bean);

    @POST(Urls.START_CHARGER)
    Observable<BaseData> getOrderDetail(@Header("AccessToken") String token, @Body OrderRequestInfo bean);
}
