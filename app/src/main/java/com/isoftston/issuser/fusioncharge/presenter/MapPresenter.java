package com.isoftston.issuser.fusioncharge.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.MapApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.Condition;
import com.isoftston.issuser.fusioncharge.model.beans.HomeAppointmentBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeChargeOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.PileFeeBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequesHomeMapInfo;
import com.isoftston.issuser.fusioncharge.model.beans.RequestChargeStateBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestFeeBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestHomeAppointment;
import com.isoftston.issuser.fusioncharge.model.beans.RequestOnlyUserId;
import com.isoftston.issuser.fusioncharge.utils.ChoiceManager;
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
        if(ChoiceManager.getInstance().getType()==0){
            condition.pileType=3;
        }else{
            condition.pileType= ChoiceManager.getInstance().getType();
        }
        if(ChoiceManager.getInstance().getStatue()==0){
            condition.chargingMethod=3;
        }else{
            condition.chargingMethod=ChoiceManager.getInstance().getStatue();
        }

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
    public void getInfo(final long id,String type){
        RequesHomeMapInfo bean=new RequesHomeMapInfo();
        bean.id=id;
        bean.type=type;
        view.showLoading();
        api.getHomeMapInfo(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<MapInfoBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<MapInfoBean>>(view) {
                    @Override
                    public void success(BaseData<MapInfoBean> baseData) {
                        view.getMarkInfo(id,baseData.data);
                    }
                });

    }

    /**
     * 获取费率信息
     */
    public void getFeeInfo(long id){
        RequestFeeBean bean =new RequestFeeBean();
        bean.chargePileId=id;
        view.showLoading();
        api.getFeeData(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<PileFeeBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<PileFeeBean>>(view) {
                    @Override
                    public void success(BaseData<PileFeeBean> baseData) {
//                        view.getMarkInfo(baseData.data);
                        view.showPileFeeInfo(baseData.data);
                    }
                });
    }

    public void getUserOrderStatue(){

//        RequestOnlyUserId bean =new RequestOnlyUserId();
//        bean.userId=1;
        api.getUserOrderStatue(UserHelper.getSavedUser().token)
                .compose(new ResponseTransformer<>(this.<BaseData<HomeOrderBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<HomeOrderBean>>(view) {
                    @Override
                    public void success(BaseData<HomeOrderBean> baseData) {
                        if(baseData.data==null){
//                            \ Response Json: {"code":0,"data":{"orderRecordNum":"1524387088804002","chargeId":0,"id":13,"userId":1}}
                            Log.e("yzh","no order");
                            view.hasNoPayOrder(false,null);
                        }else{
                            view.hasNoPayOrder(true,baseData.data);
                        }
                    }
                });
    }

    public void getUserChargeStatue(){
        RequestOnlyUserId bean =new RequestOnlyUserId();
        bean.userId=1;
        api.getUserChargerStatue(UserHelper.getSavedUser().token)
                .compose(new ResponseTransformer<>(this.<BaseData<HomeChargeOrderBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<HomeChargeOrderBean>>(view) {
                    @Override
                    public void success(BaseData<HomeChargeOrderBean> baseData) {
                        if(baseData.data!=null){
                            view.renderHomeChargerOrder(true,baseData.data);
                        }else{
                            view.renderHomeChargerOrder(false,null);
                        }
                    }
                });
    }

    public void getUserAppointment(){
        RequestHomeAppointment bean =new RequestHomeAppointment();
        bean.appUserId=UserHelper.getSavedUser().appUserId+"";
        api.getUserAppointmentRecord(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<HomeAppointmentBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<HomeAppointmentBean>>(view) {
                    @Override
                    public void success(BaseData<HomeAppointmentBean> baseData) {
                        if(baseData.data!=null){
                            view.renderAppoinmentInfo(true,baseData.data);
                        }else{
                            view.renderAppoinmentInfo(false,null);
                        }
                    }
                });
    }
    public static String token="29ed3f125b9446ebb3e5207a2ad6d3d6";
    public void getCheckStatue(String virtualId,String gunCode){
        RequestChargeStateBean bean =new RequestChargeStateBean();
        bean.virtualId="000001";
        bean.gunCode="1";
        api.getCheckStatue(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {

                    }
                });
    }
}
