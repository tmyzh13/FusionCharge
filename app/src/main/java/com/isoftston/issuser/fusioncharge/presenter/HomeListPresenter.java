package com.isoftston.issuser.fusioncharge.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.fusioncharge.model.apis.MapApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.Condition;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.views.interfaces.HomeListView;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

/**
 * Created by issuser on 2018/4/23.
 */

public class HomeListPresenter extends BasePresenter<HomeListView> {

    MapApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(MapApi.class);
    }

    @Override
    public void onStart() {

    }
    private  boolean otherLoading=false;

    public void setOtherLoading(boolean otherLoading){
        this.otherLoading=otherLoading;
    }

    public void getDatas(){
        if(otherLoading){
        }else{
            view.showLoading();
        }
        doGetDatas(newCondition());
    }

    public void getDatas(String stationName){
        Condition condition = newCondition();
        condition.stationName =stationName;
        doGetDatas(condition);
    }

    private Condition newCondition(){
        Condition condition=new Condition();
        condition.x1=100;
        condition.x2=200;
        condition.y1=30;
        condition.y2=40;
        condition.selectType=3;
        return condition;
    }
    private void doGetDatas(Condition condition) {
        api.getMapDatas(condition)
                .compose(new ResponseTransformer<>(this.<BaseData<List<MapDataBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<MapDataBean>>>(view) {
                    @Override
                    public void success(BaseData<List<MapDataBean>> baseData) {
                        view.rendData(baseData.data);
                    }
                });
    }
}
