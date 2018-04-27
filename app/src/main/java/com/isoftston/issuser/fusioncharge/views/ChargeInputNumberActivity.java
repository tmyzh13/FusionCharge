package com.isoftston.issuser.fusioncharge.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.google.gson.Gson;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.LoginApi;
import com.isoftston.issuser.fusioncharge.model.apis.MapApi;
import com.isoftston.issuser.fusioncharge.model.apis.ScanApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestChargeStateBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestScanBean;
import com.isoftston.issuser.fusioncharge.model.beans.ScanChargeInfo;
import com.isoftston.issuser.fusioncharge.model.beans.UserBean;
import com.isoftston.issuser.fusioncharge.utils.SharePrefsUtils;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.weights.NavBar;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChargeInputNumberActivity extends BaseActivity {

    private ScanApi api;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.et_input_number)
    EditText etInputNumber;
    @Bind(R.id.sure)
    TextView sure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_input_number;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(this.getString(R.string.charge));

        api = ApiFactory.getFactory().create(ScanApi.class);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.sure)
    public void onViewClicked() {
        String str = etInputNumber.getText().toString();
        long number = 0;
        try {
            number = Long.parseLong(str);
        } catch (Exception e){
            showToast("请输入正确的二维码!");
            return;
        }

        getData(number);
    }

    private void getData(long number){
        showLoading();

        if(UserHelper.getSavedUser()==null|| Tools.isNull(UserHelper.getSavedUser().token)){
            startActivity(LoginActivity.getLauncher(this));
            hideLoading();
            return;
        }

        RequestScanBean bean = new RequestScanBean();
        bean.setAppUserId(UserHelper.getSavedUser().appUserId + "");
        bean.setQrCode(number + "");

        api.getScanChargeInfo(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<ScanChargeInfo>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<ScanChargeInfo>>() {
                               @Override
                               public void success(BaseData<ScanChargeInfo> baseData) {
                                   hideLoading();
                                   if(baseData.data != null) {
                                       Gson gson = new Gson();
                                       String data = gson.toJson(baseData.data);
                                       Intent intent = new Intent(ChargeInputNumberActivity.this,ChargeOrderDetailsActivity.class);
                                       intent.putExtra("data",data);
                                       startActivity(intent);
                                   } else {
                                       showToast(getString(R.string.no_user_info));
                                   }

                                   finish();
                               }

                               @Override
                               public boolean operationError(BaseData<ScanChargeInfo> scanChargeInfoBaseData, int status, String message) {
                                   hideLoading();
                                   if(scanChargeInfoBaseData.code == 403) {
                                       goLogin();
                                   }
                                   showToast("未获取数据，请重新输入！");
                                   return super.operationError(scanChargeInfoBaseData, status, message);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   hideLoading();
                                   showToast(getString(R.string.time_out));
                               }
                           }
                );


     /*   LoginApi api1 = ApiFactory.getFactory().create(LoginApi.class);
        LoginRequestBean bean1 = new LoginRequestBean();
        bean1.phone = "17366206080";
//        bean.carrier = Tools.getPhoneType();
        bean1.carrier = "iphone";
        bean1.type = 0;
        if (0 == 0) {//密码登录
            bean1.passWord = "123";
        }
        api1.login(bean1)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        Log.e("zw",baseData.toString());

                    }
                });*/
    }

    @Override
    public void goLogin() {
        ToastMgr.show(getString(R.string.login_fail));
        UserHelper.clearUserInfo(UserBean.class);
        startActivity(LoginActivity.getLauncher(ChargeInputNumberActivity.this));
    }
}
