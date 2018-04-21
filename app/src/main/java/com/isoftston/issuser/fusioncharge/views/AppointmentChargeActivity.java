package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;

public class AppointmentChargeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = AppointmentChargeActivity.class.getSimpleName();
    private Context context = AppointmentChargeActivity.this;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.appoint_time_rl)
    RelativeLayout appointTimeRl;
    @Bind(R.id.complete_appoint_tv)
    TextView completeAppointTv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_charge;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(context.getString(R.string.appoint_charge));
        clicks();
    }

    private void clicks() {
        appointTimeRl.setOnClickListener(this);
        completeAppointTv.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appoint_time_rl:

                break;
            case R.id.complete_appoint_tv:
                startActivity(AppointSuccessActivity.getLauncher(context));
                break;
        }
    }
}
