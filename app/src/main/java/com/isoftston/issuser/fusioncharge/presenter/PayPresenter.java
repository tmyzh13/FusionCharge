package com.isoftston.issuser.fusioncharge.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.MapApi;
import com.isoftston.issuser.fusioncharge.model.apis.PayApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.PayInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestPayBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestPayDetailBean;
import com.isoftston.issuser.fusioncharge.views.interfaces.PayView;

/**
 * Created by issuser on 2018/4/25.
 */

public class PayPresenter extends BasePresenter<PayView> {

    private PayApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(PayApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getPayDetailInfo(String orderNum){
        RequestPayDetailBean bean=new RequestPayDetailBean();
        bean.orderRecordNum=orderNum;
        view.showLoading();
        api.getPayDetail(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<PayInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<PayInfoBean>>(view) {
                    @Override
                    public void success(BaseData<PayInfoBean> baseData) {
                        view.renderData(baseData.data);
                    }
                });
    }

    public void payAction(String orderNum,double total,int payType){
        RequestPayBean bean=new RequestPayBean();
        bean.orderRecordNum=orderNum;
        bean.payType=payType;
        bean.totalFee=total;
        view.showLoading();
        api.balancePay(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.paySuccess();
                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        if(status==214){
                            //余额不足
                            Log.e("yzh","余额不足");
                            view.payBalanceNotEnough();
                        }else{
                            view.payFail();
                        }
                        return true;
                    }
                });
    }
}
