package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.beans.AppointResponseBean;
import com.isoftston.issuser.fusioncharge.model.beans.UserBean;
import com.isoftston.issuser.fusioncharge.presenter.AppointPresenter;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.interfaces.AppointView;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

public class AppointmentChargeActivity extends BaseActivity<AppointView, AppointPresenter> implements View.OnClickListener, AppointView {

    private static final String TAG = AppointmentChargeActivity.class.getSimpleName();
    private Context context = AppointmentChargeActivity.this;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.appoint_time_rl)
    RelativeLayout appointTimeRl;
    @Bind(R.id.complete_appoint_tv)
    TextView completeAppointTv;
    @Bind(R.id.chosed_time_tv)
    TextView userChosedTimeTv;

    private int appointTime = 15;
    private String gunCode;
    private int chargingPileId;
    private String chargingPileName;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, AppointmentChargeActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_charge;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        gunCode = getIntent().getStringExtra("gunCode");
        chargingPileId = getIntent().getIntExtra("chargingPileId",0);
        chargingPileName = getIntent().getStringExtra("chargingPileName");

        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(context.getString(R.string.appoint_charge));
        clicks();
    }

    private void clicks() {
        appointTimeRl.setOnClickListener(this);
        completeAppointTv.setOnClickListener(this);
    }

    @Override
    protected AppointPresenter createPresenter() {
        return new AppointPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appoint_time_rl:
                showDialog();
                break;
            case R.id.complete_appoint_tv:
                showLoading();


                if(UserHelper.getSavedUser()==null|| Tools.isNull(UserHelper.getSavedUser().token)){
                    startActivity(LoginActivity.getLauncher(this));
                    return;
                }
                presenter.appointAocation(gunCode,chargingPileId,chargingPileName,getNowTime(),getEndTime(),appointTime);
                break;
        }
    }

    TextView time15mTv, time30mTv, time1hTv, time2hTv, sureTv;
    View line1, line2, line3;
    //15m  30m   1h  2h
    int item = 1;

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentChargeActivity.this);
        View view = View.inflate(AppointmentChargeActivity.this, R.layout.chose_appoint_time_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        time15mTv = view.findViewById(R.id.time_15m_tv);
        line1 = view.findViewById(R.id.cut_line1);

        time30mTv = view.findViewById(R.id.time_30m_tv);
        line2 = view.findViewById(R.id.cut_line2);

        time1hTv = view.findViewById(R.id.time_1h_tv);
        line3 = view.findViewById(R.id.cut_line3);
        time2hTv = view.findViewById(R.id.time_2h_tv);
        sureTv = view.findViewById(R.id.sure_time_tv);


        time15mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                time15mTv.setTextColor(getResources().getColor(R.color.app_blue));
                time30mTv.setTextColor(getResources().getColor(R.color.text_gray));
                time1hTv.setTextColor(getResources().getColor(R.color.text_gray));
                time2hTv.setTextColor(getResources().getColor(R.color.text_gray));
                item = 1;
            }
        });

        time30mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.GONE);
                time30mTv.setTextColor(getResources().getColor(R.color.app_blue));
                time15mTv.setTextColor(getResources().getColor(R.color.text_gray));
                time1hTv.setTextColor(getResources().getColor(R.color.text_gray));
                time2hTv.setTextColor(getResources().getColor(R.color.text_gray));
                item = 2;
            }
        });

        time1hTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.VISIBLE);
                time1hTv.setTextColor(getResources().getColor(R.color.app_blue));
                time15mTv.setTextColor(getResources().getColor(R.color.text_gray));
                time30mTv.setTextColor(getResources().getColor(R.color.text_gray));
                time2hTv.setTextColor(getResources().getColor(R.color.text_gray));
                item = 3;
            }
        });

        time2hTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.VISIBLE);
                time2hTv.setTextColor(getResources().getColor(R.color.app_blue));
                time15mTv.setTextColor(getResources().getColor(R.color.text_gray));
                time30mTv.setTextColor(getResources().getColor(R.color.text_gray));
                time1hTv.setTextColor(getResources().getColor(R.color.text_gray));
                item = 4;
            }
        });
        if (item == 1) {
            time15mTv.setSelected(true);
            time30mTv.setSelected(false);
            time1hTv.setSelected(false);
            time2hTv.setSelected(false);
        } else if (item == 2) {
            time30mTv.setSelected(true);
            time15mTv.setSelected(false);
            time1hTv.setSelected(false);
            time2hTv.setSelected(false);
        } else if (item == 3) {
            time1hTv.setSelected(true);
            time15mTv.setSelected(false);
            time30mTv.setSelected(false);
            time2hTv.setSelected(false);
        } else if (item == 4) {
            time2hTv.setSelected(true);
            time15mTv.setSelected(false);
            time30mTv.setSelected(false);
            time1hTv.setSelected(false);
        }
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item == 1) {
                    appointTime = 15;
                    userChosedTimeTv.setText(R.string.appoint_15m);
                } else if (item == 2) {
                    appointTime = 30;
                    userChosedTimeTv.setText(R.string.time_30m);
                } else if (item == 3) {
                    appointTime = 60;
                    userChosedTimeTv.setText(R.string.time_1h);
                } else if (item == 4) {
                    appointTime = 120;
                    userChosedTimeTv.setText(R.string.time_2h);
                }
                dialog.dismiss();
                getNowTime();
                getEndTime();
            }
        });
    }

    @Override
    public void appointSuccess(AppointResponseBean bean) {
        hideLoading();
        startActivity(AppointSuccessActivity.getLauncher(context,item));
    }

    @Override
    public void goLogin() {
        ToastMgr.show(getString(R.string.login_fail));
        UserHelper.clearUserInfo(UserBean.class);
        startActivity(LoginActivity.getLauncher(AppointmentChargeActivity.this));
    }

    private String getNowTime(){
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(currentTime);
        Log.e("zw","time : " + now);
        return now;
    }

    private String getEndTime(){
        long endTime = System.currentTimeMillis() + 1000 * 60 * appointTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(endTime);
        Log.e("zw","time end : " + now);
        return now;
    }
}
