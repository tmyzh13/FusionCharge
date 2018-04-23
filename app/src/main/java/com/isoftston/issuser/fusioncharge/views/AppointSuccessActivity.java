package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
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
    }


    @OnClick(R.id.action_go_tv)
    public void actionGo() {

    }

    @OnClick(R.id.action_guild_tv)
    public void actionGuild() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
