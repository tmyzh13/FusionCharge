package com.isoftston.issuser.fusioncharge.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.fusioncharge.model.apis.MapApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.Condition;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequesHomeMapInfo;
import com.isoftston.issuser.fusioncharge.views.interfaces.MapHomeView;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

/**
 * Created by issuser on 2018/4/23.
 */

public class MapPresenter extends BasePresenter<MapHomeView> {

    private MapApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(MapApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getData(){
        Condition condition=new Condition();
        condition.selectType=3;
//        condition.pileType=1;
//        condition.chargingMethod=1;
//        condition.pileName="";
//        condition.stationName="";
        condition.x1=100;
        condition.x2=200;
        condition.y1=30;
        condition.y2=40;
//        condition.workStatus=1;
        api.getMapDatas(condition)
                .compose(new ResponseTransformer<>(this.<BaseData<List<MapDataBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<MapDataBean>>>(view) {
                    @Override
                    public void success(BaseData<List<MapDataBean>> baseData) {
                        view.renderMapData(baseData.data);
                    }
                });
    }
//"id":"1",
//        "type":"pile"//pile桩、station站
    public void getInfo(long id,String type){
        RequesHomeMapInfo bean=new RequesHomeMapInfo();
        bean.id=id;
        bean.type=type;
        view.showLoading();
        api.getHomeMapInfo(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<MapInfoBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<MapInfoBean>>(view) {
                    @Override
                    public void success(BaseData<MapInfoBean> baseData) {
                        view.getMarkInfo(baseData.data);
                    }
                });

    }
}
