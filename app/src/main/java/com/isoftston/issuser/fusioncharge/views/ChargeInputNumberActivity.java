package com.isoftston.issuser.fusioncharge.views;

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
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.apis.LoginApi;
import com.isoftston.issuser.fusioncharge.model.apis.MapApi;
import com.isoftston.issuser.fusioncharge.model.apis.ScanApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestChargeStateBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestScanBean;
import com.isoftston.issuser.fusioncharge.model.beans.ScanChargeInfo;
import com.isoftston.issuser.fusioncharge.utils.SharePrefsUtils;
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

        getData();
    }

    public static String token="16d4f37c795f4e0e80df079432285614";
    private void getData(){
        showLoading();

        RequestScanBean bean = new RequestScanBean();
        bean.setAppUserId( 67 + "");
        bean.setQrCode("1000000001");

      /*  api.getScanChargeInfo(token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<ScanChargeInfo>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<ScanChargeInfo>>() {
                               @Override
                               public void success(BaseData<ScanChargeInfo> baseData) {
                                    Log.e("zw"," null ? : " + (baseData == null) );
                                    Log.e("zw",baseData.toString());
                               }

                               @Override
                               public boolean operationError(BaseData<ScanChargeInfo> scanChargeInfoBaseData, int status, String message) {
                                   hideLoading();
                                   showToast("未获取数据，请重新输入！");
                                   Log.e("zw"," null ? : " + (scanChargeInfoBaseData.data == null) );
                                   Log.e("zw",scanChargeInfoBaseData.data.toString());
                                   return super.operationError(scanChargeInfoBaseData, status, message);
                               }
                           }
                );*/


        LoginApi api1 = ApiFactory.getFactory().create(LoginApi.class);
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
                });
    }
}
