package com.isoftston.issuser.fusioncharge.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.ChargeStatueApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.ChargerStatueBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestEndChargerBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestStartChargerBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequstChargeStatueBean;
import com.isoftston.issuser.fusioncharge.views.interfaces.ChargerStatueView;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/4/24.
 */

public class ChargeStatuePresenter extends BasePresenter<ChargerStatueView> {

    private ChargeStatueApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(ChargeStatueApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getChargeStatue(String virtualId,String gunCode){
        RequstChargeStatueBean bean =new RequstChargeStatueBean();
        bean.virtualId=virtualId;
        bean.gunCode=gunCode;
        api.getChargeStatue(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<ChargerStatueBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<ChargerStatueBean>>(view) {
                    @Override
                    public void success(BaseData<ChargerStatueBean> baseData) {
                        if(baseData.data!=null){
                            view.renderChargerStatueData(baseData.data);
                        }

                    }
                });
    }

    public void startCharging(){
        RequestStartChargerBean bean =new RequestStartChargerBean();
        api.startCharge(bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {

                    }
                });
    }

    public void endCharging(long chargeingPileId,String chargingPileName,String virtualId,
                            String gunCode,String orderRecordNum){
        RequestEndChargerBean bean =new RequestEndChargerBean();
        bean.chargingPileId=chargeingPileId;
        bean.chargingPileName=chargingPileName;
        bean.virtualId=virtualId;
        bean.appUserId=67;
        bean.gunCode=gunCode;
        bean.stopReason="1";
        bean.orderRecordNum=orderRecordNum;
        api.stopCharge(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        if(baseData!=null&&baseData.data!=null){
                            view.endChargeSuccess();
                        }else{
                            view.endChargeFail();
                        }
                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        view.endChargeFail();
                        return super.operationError(baseData, status, message);
                    }
                });
    }
}
