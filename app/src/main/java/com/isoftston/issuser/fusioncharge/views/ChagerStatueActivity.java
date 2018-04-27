package com.isoftston.issuser.fusioncharge.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.beans.ChargerStatueBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeChargeOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.UserBean;
import com.isoftston.issuser.fusioncharge.presenter.ChargeStatuePresenter;
import com.isoftston.issuser.fusioncharge.utils.TimeServiceManager;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.interfaces.ChargerStatueView;
import com.isoftston.issuser.fusioncharge.weights.CheckChargeFailDialog;
import com.isoftston.issuser.fusioncharge.weights.CheckStatueLoadingView;
import com.isoftston.issuser.fusioncharge.weights.CircleProgressView;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/20.
 */

public class ChagerStatueActivity extends BaseActivity<ChargerStatueView, ChargeStatuePresenter> implements ChargerStatueView {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.tv_charge_time)
    TextView tv_charge_time;
    @Bind(R.id.progress)
    CircleProgressView progressView;
    @Bind(R.id.tv_address_name)
    TextView tv_address_name;
    @Bind(R.id.tv_kw)
    TextView tv_kw;
    @Bind(R.id.tv_current_charger)
    TextView tv_current_charge;
    @Bind(R.id.tv_charged_enegy)
    TextView tv_charged_enegy;
    @Bind(R.id.tv_charged_money)
    TextView tv_charged_money;

    private Context context = ChagerStatueActivity.this;
    private CommonDialog commonDialog;
    private CheckStatueLoadingView checkStatueLoadingView;
    private CheckChargeFailDialog dialog;
    private TimerService timerService;
    private HomeChargeOrderBean homeChargeOrderBean;
    private Timer timer;
    private Handler handler;

    public static Intent getLauncher(Context context, HomeChargeOrderBean bean) {
        Intent intent = new Intent(context, ChagerStatueActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        intent.putExtra("bundle", bundle);
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

        handler=new Handler();
        commonDialog = new CommonDialog(context, getString(R.string.hint), getString(R.string.charging_statue_hint), 2);
        dialog = new CheckChargeFailDialog(context);
        checkStatueLoadingView = new CheckStatueLoadingView(context, getString(R.string.charging_statue_checking));
        Bundle bundle = getIntent().getBundleExtra("bundle");
        homeChargeOrderBean = (HomeChargeOrderBean) bundle.getSerializable("data");
//        checkStatueLoadingView.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                checkStatueLoadingView.dismiss();
//                dialog.show();
//            }
//        },2000);
//        PreferencesHelper.saveData(Constant.CHARGING_TOTAL,Tools.getHourValue("03:45"));
        //进度设置100  进度给对应的百分比
        progressView.setMax(100);
//        if(Tools.isNull(PreferencesHelper.getData(Constant.CHARGING_TIME))){
//            PreferencesHelper.saveData(Constant.CHARGING_TIME,"0");
//            tv_charge_time.setText("00:00");
//            progressView.setProgress(0);
//        }else{
//            progressView.setProgress(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME)));
//            tv_charge_time.setText(Tools.formatHour(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME))));
//        }


//        timerService.timerHour();
        RxBus.getDefault().toObservable(Object.class, Constant.CHARGING_TIME)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        Log.e("yzh", "receiver");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_charge_time.setText(Tools.formatHour(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME))));
                                progressView.setProgress(Long.parseLong(PreferencesHelper.getData(Constant.CHARGING_TIME)));
                            }
                        });

                    }
                });
        if (homeChargeOrderBean != null) {
            presenter.getChargeStatue(homeChargeOrderBean.virtualId, homeChargeOrderBean.chargeGunNum);
        }
        timer = new Timer();

    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent = new Intent(this, TimerService.class);
//        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected ChargeStatuePresenter createPresenter() {
        return new ChargeStatuePresenter();
    }


    @OnClick(R.id.tv_pay)
    public void goPay() {
        commonDialog.show();
        commonDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
                //进入支付界面
//                startActivity(PayActivity.getLauncher(context));
                //结束充电 成功之后在选择进入支付
                checkStatueLoadingView.setMessage(getString(R.string.charging_statue_ending));
                checkStatueLoadingView.show();
                presenter.endCharging(chargerStatueBean.chargingPileId,chargerStatueBean.chargingPileName,homeChargeOrderBean.virtualId,homeChargeOrderBean.chargeGunNum,chargerStatueBean.orderRecordNum);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        checkStatueLoadingView.dismiss();
//                        commonDialog.setMsg(getString(R.string.charging_statue_end_go_to_pay));
//                        commonDialog.show();
//                        commonDialog.setPositiveListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
////                                orderNum 是在传参里面有
////                                presenter.endCharging();
////                                startActivity(PayActivity.getLauncher(context,""));
//                            }
//                        });
//                    }
//                },2000);
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            TimerService.ServiceBinder binder = (TimerService.ServiceBinder) service;
//            Log.e("yzh", "11111111111");
//            timerService = binder.getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            Log.e("yzh", "22222222222222");
//            timerService.cancelTimerHour();
//            timerService = null;
        }
    };

    private ChargerStatueBean chargerStatueBean;

    @Override
    public void renderChargerStatueData(ChargerStatueBean bean) {
        chargerStatueBean=bean;
        if (bean.isStart == 0) {
            //检测失败
            final CheckChargeFailDialog dialog = new CheckChargeFailDialog(context);
            dialog.show();
            dialog.setCancelOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener()
            {
                public boolean onKey(DialogInterface dialog,
                                     int keyCode, KeyEvent event)
                {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                    {
                        finish();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            });
        } else if (bean.isStart == 1) {
            checkStatueLoadingView.dismiss();
            //检测中的定时结束
            handler.postDelayed(runnable,5000);
//            timer.cancel();
//            timer = new Timer();
//            //开始5s刷新一个数据的定时
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    presenter.getChargeStatue(homeChargeOrderBean.virtualId, homeChargeOrderBean.chargeGunNum);
//                }
//            }, 1000, 5000);

            //成功
            tv_address_name.setText(bean.chargingPileName);
            tv_kw.setText(bean.kwh);
            if(!Tools.isNull(bean.money)){
                tv_charged_money.setText(bean.money + getString(R.string.yuan));
            }

            tv_current_charge.setText(bean.soc+"%");
            tv_charged_enegy.setText(bean.power + getString(R.string.du));
            //05:04:25
            if(Tools.isNull(bean.alreadyTime)){
                tv_charge_time.setText("00:00");
            }else{
                tv_charge_time.setText(bean.alreadyTime.substring(0,5));
            }
            if(Tools.isNull(bean.alreadyTime)){
                PreferencesHelper.saveData(Constant.CHARGING_TIME, "0");
            }else{
                PreferencesHelper.saveData(Constant.CHARGING_TIME, Tools.getTimeValue(bean.alreadyTime)+"");
            }

            TimeServiceManager.getInstance().getTimerService().timerHour();
            if (Tools.isNull(bean.soc)) {
                progressView.setProgress(0);
            } else {
                progressView.setProgress(Long.parseLong(bean.soc));
            }
            if (bean.isStop != 0) {
//                timerService.cancelTimerHour();
                TimeServiceManager.getInstance().getTimerService().cancelTimerHour();
                handler.removeCallbacks(runnable);
                //弹框提示充电结束去支付
                final CommonDialog dialog = new CommonDialog(context, getString(R.string.hint), getString(R.string.charging_statue_end_go_to_pay), 2);
                dialog.show();
                dialog.setPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        while (!AppManager.getAppManager().currentActivity().getClass().equals(MainActivity.class)) {
                            AppManager.getAppManager().finishActivity();
                        }
                        startActivity(PayActivity.getLauncher(context, chargerStatueBean.orderRecordNum));
                    }
                });

                dialog.setNagitiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        while (!AppManager.getAppManager().currentActivity().getClass().equals(MainActivity.class)) {
                            AppManager.getAppManager().finishActivity();
                        }
                    }
                });
            }

        } else if (bean.isStart == 2) {
            //检测中
            checkStatueLoadingView.show();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    presenter.getChargeStatue(homeChargeOrderBean.virtualId, homeChargeOrderBean.chargeGunNum);
//                }
//            }, 1000, 3000);
            handler.postDelayed(runnable,3000);
        }
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(homeChargeOrderBean!=null){
                presenter.getChargeStatue(homeChargeOrderBean.virtualId, homeChargeOrderBean.chargeGunNum);
            }

        }
    };

    @Override
    public void endChargeSuccess() {
        checkStatueLoadingView.dismiss();
        handler.removeCallbacks(runnable);
//        timerService.cancelTimerHour();
        TimeServiceManager.getInstance().getTimerService().cancelTimerHour();
        //刷新首页的界面
        RxBus.getDefault().send(new Object(),Constant.REFRESH_HOME_STATUE);
        commonDialog.setMsg(getString(R.string.charging_statue_end_go_to_pay));
        commonDialog.show();
        commonDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                orderNum 是在传参里面有
//                                presenter.endCharging();
            startActivity(PayActivity.getLauncher(context,chargerStatueBean.orderRecordNum));
            }
        });
        commonDialog.setNagitiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStatueLoadingView.dismiss();
                while (!AppManager.getAppManager().currentActivity().getClass().equals(MainActivity.class)) {
                    AppManager.getAppManager().finishActivity();
                }
            }
        });

    }

    @Override
    public void endChargeFail() {
        checkStatueLoadingView.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
        handler.removeCallbacks(runnable);
        handler=null;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           finish();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void goLogin() {
        UserHelper.clearUserInfo(UserBean.class);
        startActivity(LoginActivity.getLauncher(context));
    }

}
