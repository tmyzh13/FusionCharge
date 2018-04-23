package com.isoftston.issuser.fusioncharge.model.apis;

import com.isoftston.issuser.fusioncharge.constants.Urls;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.Condition;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequesHomeMapInfo;
import com.isoftston.issuser.fusioncharge.model.beans.RequestFeeBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestMapBean;


import java.util.List;

import butterknife.Bind;
import retrofit2.http.Body;
import retrofit2.http.POST;
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
    Observable<BaseData> getFeeData(@Body RequestFeeBean bean);
}
