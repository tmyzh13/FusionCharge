package com.isoftston.issuser.fusioncharge.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.fusioncharge.model.apis.ChargeStatueApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
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

    public void getChargeStatue(){
        RequstChargeStatueBean bean =new RequstChargeStatueBean();
        bean.virtualId="000001";
        bean.gunCode="1";
        api.getChargeStatue(bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {

                    }
                });
    }
}
