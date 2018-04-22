package com.isoftston.issuser.fusioncharge.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.weights.CheckChargeFailDialog;
import com.isoftston.issuser.fusioncharge.weights.CheckStatueLoadingView;
import com.isoftston.issuser.fusioncharge.weights.CircleProgressView;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import java.util.Timer;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/20.
 */

public class ChagerStatueActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.tv_charge_time)
    TextView tv_charge_time;
    @Bind(R.id.progress)
    CircleProgressView progressView;

    private Context context=ChagerStatueActivity.this;
    private CommonDialog commonDialog;
    private CheckStatueLoadingView checkStatueLoadingView;
    private CheckChargeFailDialog dialog;
    private TimerService timerService;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,ChagerStatueActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_statue;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.charging_statue));
        navBar.setImageBackground(R.drawable.nan_bg);
        commonDialog=new CommonDialog(context,getString(R.string.hint),getString(R.string.charging_statue_hint),2);
        dialog=new CheckChargeFailDialog(context);
        checkStatueLoadingView=new CheckStatueLoadingView(context,getString(R.string.charging_statue_checking));
//        checkStatueLoadingView.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                checkStatueLoadingView.dismiss();
//                dialog.show();
//            }
//        },2000);
        Log.e("yzh","--"+Tools.getHourValue("03:45"));
        PreferencesHelper.saveData(Constant.CHARGING_TOTAL,Tools.getHourValue("03:45"));
        progressView.setMax(Tools.getHourValue("03:45"));
        if(Tools.isNull(PreferencesHelper.getData(Constant.CHARGING_TIME))){
            PreferencesHelper.saveData(Constant.CHARGING_TIME,"0");
            tv_charge_time.setText("00:00");
            progressView.setProgress(0);
        }else{
            progressView.setProgress(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME)));
            tv_charge_time.setText(Tools.formatHour(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME))));
        }


//        timerService.timerHour();
        RxBus.getDefault().toObservable(Object.class, Constant.CHARGING_TIME)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        Log.e("yzh","receiver");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_charge_time.setText(Tools.formatHour(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME))));
                                progressView.setProgress(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME)));
                            }
                        });

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @OnClick(R.id.tv_pay)
    public void goPay(){
        commonDialog.show();
        commonDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
                //进入支付界面
//                startActivity(PayActivity.getLauncher(context));
                checkStatueLoadingView.setMessage(getString(R.string.charging_statue_ending));
                checkStatueLoadingView.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkStatueLoadingView.dismiss();
                        commonDialog.setMsg(getString(R.string.charging_statue_end_go_to_pay));
                        commonDialog.show();
                        commonDialog.setPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(PayActivity.getLauncher(context));
                            }
                        });
                    }
                },2000);
            }
        });
    }

    private ServiceConnection mConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.ServiceBinder binder =(TimerService.ServiceBinder)service;
            Log.e("yzh","11111111111");
            timerService=binder.getService();
            timerService.timerHour();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("yzh","22222222222222");
            timerService.cancelTimerHour();
            timerService=null;
        }
    };
}
