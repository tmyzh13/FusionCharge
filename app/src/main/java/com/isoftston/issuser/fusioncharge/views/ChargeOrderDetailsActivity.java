package com.isoftston.issuser.fusioncharge.views;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.ScanApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.ChargingGunBeans;
import com.isoftston.issuser.fusioncharge.model.beans.OrderRequestInfo;
import com.isoftston.issuser.fusioncharge.model.beans.ScanChargeInfo;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.weights.NavBar;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChargeOrderDetailsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private int chargePowerCount = 0;

    private final int BASE_ID = 8888;

    public static final int WITH_POWER = 1;
    public static final int WITH_TIME = 2;
    public static final int WITH_MONEY = 3;
    public static final int WITH_FULL = 4;
    public static final int WITH_CONTROL = 5;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.charge_station_name)
    TextView chargeStationName;
    @Bind(R.id.charge_zhuang_number)
    TextView chargeZhuangNumber;
    @Bind(R.id.iv_charge_cost_ask)
    ImageView ivChargeCostAsk;
    @Bind(R.id.charge_cost)
    TextView chargeCost;
    @Bind(R.id.charge_service_cost)
    TextView chargeServiceCost;
    @Bind(R.id.rg_choose_gun)
    RadioGroup rgChooseGun;
    @Bind(R.id.rb_with_power)
    RadioButton rbWithPower;
    @Bind(R.id.rb_with_money)
    RadioButton rbWithMoney;
    @Bind(R.id.rb_with_time)
    RadioButton rbWithTime;
    @Bind(R.id.rb_with_full)
    RadioButton rbWithFull;
    @Bind(R.id.et_charge_count)
    EditText etChargeCount;
    @Bind(R.id.btn_start_charge)
    Button btnStartCharge;
    @Bind(R.id.rg_choose_power_style)
    RadioGroup rgChoosePowerStyle;
    private ScanChargeInfo scanChargeInfo;
    private ChargingGunBeans chooseGun;
    private int chooseStyle = WITH_POWER;

    private ScanApi api;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_order_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        api = ApiFactory.getFactory().create(ScanApi.class);
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(this.getString(R.string.charge_detail));

        initData();
        initView();

        rgChoosePowerStyle.setOnCheckedChangeListener(this);
        rbWithPower.setChecked(true);
        setRadioButtonDrawableSize();
    }

    private void initData(){
        String data = getIntent().getStringExtra("data");
        Gson gson = new Gson();
        scanChargeInfo = gson.fromJson(data, new TypeToken<ScanChargeInfo>() {
        }.getType());
    }

    private void initView(){
        chargeStationName.setText(scanChargeInfo.getStationName());
        chargeZhuangNumber.setText(scanChargeInfo.getRunCode());
        String chargeCostStr = "<font color='#FF0000'>" + scanChargeInfo.getFee() + "</font>" + getString(R.string.yuan_du);
        chargeCost.setText(Html.fromHtml(chargeCostStr));
        chargeServiceCost.setText(scanChargeInfo.getServiceFee() + getString(R.string.yuan_du));

        List<ChargingGunBeans> gunBeans = scanChargeInfo.getChargingGunBeans();
        if(gunBeans != null && !gunBeans.isEmpty()){

            for (int i = 0; i < scanChargeInfo.getChargingGunBeans().size(); i++) {
                LayoutInflater.from(this).inflate(R.layout.merge_charge_gun_radiobutton,rgChooseGun,true);
                RadioButton rb = (RadioButton) rgChooseGun.getChildAt(i);
                rb.setText("充电枪 ： " + scanChargeInfo.getChargingGunBeans().get(i).getGunNumber());
                rb.setId(BASE_ID + i);
                if(i == 0) {
                    rb.setChecked(true);
                    chooseGun = scanChargeInfo.getChargingGunBeans().get(i);
                }
            }
            rgChooseGun.setOnCheckedChangeListener(this);

        }

    }

    private void setRadioButtonDrawableSize() {
        Drawable[] drawables = null;
        for (int i = 0; i < rgChooseGun.getChildCount(); i++) {
            RadioButton rb = (RadioButton) rgChooseGun.getChildAt(i);
            drawables = rb.getCompoundDrawables();
            drawables[2].setBounds(0,0,Tools.dip2px(getApplicationContext(),20),Tools.dip2px(getApplicationContext(),20));
            rb.setCompoundDrawables(null,null,drawables[2],null);
        }

        for (int i = 0; i < rgChoosePowerStyle.getChildCount(); i++) {
            RadioButton rb = (RadioButton) rgChoosePowerStyle.getChildAt(i);
            drawables = rb.getCompoundDrawables();
            drawables[0].setBounds(0,0, Tools.dip2px(getApplicationContext(),20),Tools.dip2px(getApplicationContext(),20));
            drawables[2].setBounds(0,0,Tools.dip2px(getApplicationContext(),20),Tools.dip2px(getApplicationContext(),20));
            rb.setCompoundDrawables(drawables[0],null,drawables[2],null);
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.iv_charge_cost_ask, R.id.btn_start_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_charge_cost_ask:
                break;
            case R.id.btn_start_charge:
                if(chooseGun == null) {
                    ToastMgr.show(getString(R.string.no_charge_gun_cannot_charge));
                    return;
                }
                if(chooseStyle == WITH_FULL) {
                    getData();
                    return;
                }
                String count = etChargeCount.getText().toString();
                if(!TextUtils.isEmpty(count)){
                    chargePowerCount = Integer.parseInt(count);
                    if(chargePowerCount > 999 || chargePowerCount <1){
                        ToastMgr.show(getString(R.string.please_enter_1_999_int));
                        return;
                    } else {
                        getData();
                    }
                } else {
                    ToastMgr.show(getString(R.string.please_input_charge_count));
                }

                break;
        }
    }

    private void getData(){
        showLoading();

        OrderRequestInfo info = new OrderRequestInfo();
        Log.e("zw",scanChargeInfo.toString());
        info.setChargingPileName(scanChargeInfo.getChargingPileName());
        //TODO 后续替换
//        info.setVirtualId("020001");
        info.setVirtualId(scanChargeInfo.getVirtualId());
//        info.setAppUserId(71 + "");
        info.setAppUserId(scanChargeInfo.getAppUserId());
//        info.setGunCode(2 + "");
        info.setGunCode(chooseGun.getGunCode());
//        info.setControlInfo(2 + "");
        info.setControlInfo(chooseStyle + "");
//        info.setChargingPileId(1);
        info.setChargingPileId(scanChargeInfo.getChargingPileId());
        if(chooseStyle != WITH_FULL){
//            info.setControlData(320 + "");
            info.setControlData(chargePowerCount + "");
        }
        if(UserHelper.getSavedUser()==null||Tools.isNull(UserHelper.getSavedUser().token)){
            startActivity(LoginActivity.getLauncher(this));
        } else {
            api.getOrderDetail(UserHelper.getSavedUser().token,info)
                    .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                    .subscribe(new ResponseSubscriber<BaseData>() {
                        @Override
                        public void success(BaseData baseData) {
                            Log.e("zw","info : success");
                            Log.e("zw",baseData.toString());
                            hideLoading();
                        }

                        @Override
                        public boolean operationError(BaseData baseData, int status, String message) {
                            hideLoading();
                            showToast(getString(R.string.wrong_request));
                            return super.operationError(baseData, status, message);
                        }
                    });
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码已取消", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()){
            //充电方式选择
            case R.id.rg_choose_power_style:
                if(i == rbWithFull.getId()){
                    etChargeCount.setEnabled(false);
                    etChargeCount.setClickable(false);
                    etChargeCount.setHint(R.string.choose_charge_full);
                    chooseStyle = WITH_FULL;
                    Log.e("zw",chooseStyle + "chooseInfo :WITH_FULL " );
                }else {
                    etChargeCount.setEnabled(true);
                    etChargeCount.setClickable(true);
                    etChargeCount.setHint(R.string.please_enter_charge_count);
                    if(i == rbWithPower.getId()){
                        chooseStyle = WITH_POWER;
                        Log.e("zw",chooseStyle +"chooseInfo :WITH_POWER " );
                    }else if(i == rbWithMoney.getId()){
                        chooseStyle = WITH_MONEY;
                        Log.e("zw",chooseStyle +"chooseInfo :WITH_MONEY " );
                    } else if(i == rbWithTime.getId()){
                        chooseStyle = WITH_TIME; //2
                        Log.e("zw",chooseStyle +"chooseInfo :WITH_TIME " );
                    }
                }
                break;
                //充电枪选择
            case R.id.rg_choose_gun:
                int index = i - BASE_ID;

                chooseGun = scanChargeInfo.getChargingGunBeans().get(index);
//                chooseGun = scanChargeInfo.getChargingGunBeans().get(i);
//                Log.e("zw","chooseGun : " + chooseGun.getGunNumber() );
                break;
        }
    }

    @Override
    public void goLogin() {

    }
}
