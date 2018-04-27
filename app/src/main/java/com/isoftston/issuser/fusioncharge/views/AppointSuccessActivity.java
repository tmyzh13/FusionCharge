package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.beans.HomeAppointmentBean;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

public class AppointSuccessActivity extends BaseActivity {
    private static final String TAG = AppointSuccessActivity.class.getSimpleName();
    private Context context = AppointSuccessActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.appoint_date_tv)
    TextView appiontDateTv;
    @Bind(R.id.arrive_on_time_tv)
    TextView arriveOnTimeTv;
    @Bind(R.id.action_go_tv)
    TextView actionGoTv;
    @Bind(R.id.action_guild_tv)
    TextView actionGuildTv;

    private int time;
    private String gunCode;
    private int chargingPileId;
    private String chargingPileName;
    private double latitude;
    private double longitude;
    private String runCode;
    private String address;

    public static Intent getLauncher(Context context, int time) {
        Intent intent = new Intent(context, AppointSuccessActivity.class);
        intent.putExtra("time", 0);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appoint_success;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(context.getString(R.string.appoint_success));
        time = getIntent().getIntExtra("time", 0);
        if (time == 1) {
            arriveOnTimeTv.setText(R.string.appoint_15m);
        } else if (time == 2) {
            arriveOnTimeTv.setText(R.string.appoint_30m);
        } else if (time == 3) {
            arriveOnTimeTv.setText(R.string.appoint_1h);
        } else if (time == 4) {
            arriveOnTimeTv.setText(R.string.appoint_2h);
        }
        appiontDateTv.setText(Tools.getYearMonthDate());

        gunCode = getIntent().getStringExtra("gunCode");
        chargingPileId = getIntent().getIntExtra("chargingPileId",0);
        chargingPileName = getIntent().getStringExtra("chargingPileName");
        runCode = getIntent().getStringExtra("runCode");
        address = getIntent().getStringExtra("address");

        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);
    }


    @OnClick(R.id.action_go_tv)
    public void actionGo() {
        while (!AppManager.getAppManager().currentActivity().getClass().equals(MainActivity.class)){
            AppManager.getAppManager().finishActivity();
        }
    }

    @OnClick(R.id.action_guild_tv)
    public void actionGuild() {
        while (!AppManager.getAppManager().currentActivity().getClass().equals(MainActivity.class)){
            AppManager.getAppManager().finishActivity();
        }

        HomeAppointmentBean bean = new HomeAppointmentBean();
        bean.chargingAddress = address;
        bean.chargingPileId = chargingPileId;
        bean.chargingPileName = chargingPileName;
        bean.gunCode = gunCode;
        bean.latitude = latitude;
        bean.longitude = longitude;
        bean.runCode = runCode;

        Log.e("zw","address : " + address + "  id : " + chargingPileId
                 + " name : " + chargingPileName + " gunCode : " + gunCode + ",lat : " + latitude
                + " ,lon : " + longitude + " ,runCode : " + runCode);

        startActivity(GuildActivity.getLauncher(this,latitude,longitude,bean,false));
        this.finish();

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void goLogin() {

    }
}
