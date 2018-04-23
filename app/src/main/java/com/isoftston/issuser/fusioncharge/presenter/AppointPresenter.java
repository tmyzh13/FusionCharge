package com.isoftston.issuser.fusioncharge.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.fusioncharge.model.apis.AppointApi;
import com.isoftston.issuser.fusioncharge.model.beans.AppointRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.views.interfaces.AppointView;

/**
 * Created by issuser on 2018/4/23.
 */

public class AppointPresenter extends BasePresenter<AppointView> {

    AppointApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(AppointApi.class);
    }

    @Override
    public void onStart() {
    }

    public void appointAocation() {
        AppointRequestBean bean = new AppointRequestBean();

        api.appoint(bean)
                .compose(new ResponseTransformer<BaseData>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        Log.e("appointAocation", "----success");
                        view.appointSuccess();
                    }
                });
    }
}
