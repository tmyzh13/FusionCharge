package com.isoftston.issuser.fusioncharge.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChargeOrderDetailsActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_order_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(this.getString(R.string.charge_detail));
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
                break;
        }
    }
}
