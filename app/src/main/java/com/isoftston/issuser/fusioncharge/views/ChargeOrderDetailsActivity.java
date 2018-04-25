package com.isoftston.issuser.fusioncharge.views;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.weights.NavBar;
import com.journeyapps.barcodescanner.CaptureActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChargeOrderDetailsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_order_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(this.getString(R.string.charge_detail));

        rgChoosePowerStyle.setOnCheckedChangeListener(this);
        setRadioButtonDrawableSize();
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
                //进入扫一扫界面
                new IntentIntegrator(this)
                        .setCaptureActivity(ChargeCaptureActivity.class)
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                        .setPrompt("请对准二维码")// 设置提示语
                        .setCameraId(0)// 选择摄像头,可使用前置或者后置
                        .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                        .setBarcodeImageEnabled(false)// 扫完码之后生成二维码的图片
                        .initiateScan();// 初始化扫码
                break;
        }
    }


    @Override
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
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()){
            case R.id.rg_choose_power_style:
                if(i == rbWithFull.getId()){
                    etChargeCount.setEnabled(false);
                    etChargeCount.setClickable(false);
                    etChargeCount.setHint(R.string.choose_charge_full);
                }else {
                    etChargeCount.setEnabled(true);
                    etChargeCount.setClickable(true);
                    etChargeCount.setHint(R.string.please_enter_charge_count);
                }
                break;


        }
    }
}
