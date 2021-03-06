package com.isoftston.issuser.fusioncharge.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.beans.HomeAppointmentBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeRefreshBean;
import com.isoftston.issuser.fusioncharge.model.beans.MyLocationBean;
import com.isoftston.issuser.fusioncharge.presenter.GuaildPresenter;
import com.isoftston.issuser.fusioncharge.utils.DrivingRouteOverLay;
import com.isoftston.issuser.fusioncharge.utils.TimeServiceManager;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.interfaces.GuaildView;
import com.isoftston.issuser.fusioncharge.weights.AppointmentTimeOutDialog;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/23.
 */

public class GuildActivity  extends BaseActivity<GuaildView,GuaildPresenter> implements GuaildView, RouteSearch.OnRouteSearchListener {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.ll_appointment)
    LinearLayout ll_appointment;
    @Bind(R.id.tv_appointment_address)
    TextView tv_appointment_address;
    @Bind(R.id.tv_appointment_time)
    TextView tv_appointment_time;
    @Bind(R.id.iv_hint)
    ImageView iv_hint;
    @Bind(R.id.ll_hint)
    LinearLayout ll_hint;
    @Bind(R.id.tv_pile_num)
    TextView tv_pile_num;
    @Bind(R.id.tv_pile_name)
    TextView tv_pile_name;
    @Bind(R.id.tv_gun_num)
    TextView tv_gun_num;
    @Bind(R.id.ll_time)
    LinearLayout ll_time;

    private AMap aMap;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private final int ROUTE_TYPE_DRIVE = 2;

    private LatLonPoint mStartPoint = null;//起点，116.335891,39.942295
    private LatLonPoint mEndPoint = null;//终点，116.481288,39.995576
    private Context context=GuildActivity.this;
    private DrivingRouteOverLay drivingRouteOverlay;
    private double endLatitude,endLongitude;
    private long appointmentTime;
    private HomeAppointmentBean homeAppointmentBean;
    private CommonDialog commonDialog;
    private boolean choiceNotAppointment;
    private AppointmentTimeOutDialog appointmentTimeOutDialog;
    private Timer timerAppointment;

    public static Intent getLauncher(Context context,double latitude,double longitude,HomeAppointmentBean bean,boolean choiceNotAppoinment){
        Intent intent =new Intent(context,GuildActivity.class);
        intent.putExtra("la",latitude);
        intent.putExtra("ln",longitude);
        Bundle bundle =new Bundle();
        bundle.putSerializable("bean",bean);
        intent.putExtra("bundle",bundle);
        intent.putExtra("choice",choiceNotAppoinment);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guaild;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.guilding));
        navBar.setImageBackground(R.drawable.nan_bg);

        timerAppointment=new Timer();
        commonDialog=new CommonDialog(context,"",2);

        endLatitude=getIntent().getDoubleExtra("la",0);
        endLongitude=getIntent().getDoubleExtra("ln",0);
        homeAppointmentBean=(HomeAppointmentBean) getIntent().getBundleExtra("bundle").getSerializable("bean");
        choiceNotAppointment=getIntent().getBooleanExtra("choice",false);

        appointmentTimeOutDialog=new AppointmentTimeOutDialog(context);

        if(choiceNotAppointment){
            //提示是否继续导航
            commonDialog=new CommonDialog(context,"",2);
            commonDialog.show();
            commonDialog.setMsg(getString(R.string.guilding_check_not_appointment));
            commonDialog.setNagitiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismiss();
                    finish();
                }
            });
        }else{

        }

        if(homeAppointmentBean!=null){
            navBar.showCancelAppointment(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog=new CommonDialog(context,"",2);
                    commonDialog.show();
                    commonDialog.setDialogBackground();
                    commonDialog.setMsg(getString(R.string.guilding_cancel_appointment_hint));
                    commonDialog.setPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            commonDialog.dismiss();
                            //调用接口
                            presenter.cancelAppointment(homeAppointmentBean.reserveId+"", UserHelper.getSavedUser().appUserId+"",homeAppointmentBean.gunCode,homeAppointmentBean.chargingPileId+"");
                        }
                    });
                }
            });
            ll_appointment.setVisibility(View.VISIBLE);

            tv_pile_num.setText(homeAppointmentBean.runCode);
            tv_pile_name.setText(homeAppointmentBean.chargingPileName);
            tv_gun_num.setText(homeAppointmentBean.gunCode);
            tv_appointment_address.setText(homeAppointmentBean.chargingAddress);
            tv_appointment_time.setText(Tools.formatMinute(Long.parseLong(PreferencesHelper.getData(Constant.TIME_APPOINTMENT))));
            //设置时间开始计时
//            Intent intent = new Intent(this, TimerService.class);
//            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            timerAppointment.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(homeAppointmentBean!=null){
                        if(Tools.isNull(PreferencesHelper.getData(Constant.TIME_APPOINTMENT))){
                            appointmentTime=0;
                        }else{
                            appointmentTime=Long.parseLong(PreferencesHelper.getData(Constant.TIME_APPOINTMENT));
                        }
                        appointmentTime-=1000;
                        Log.e("yzh","sssss----"+appointmentTime);
                        PreferencesHelper.saveData(Constant.TIME_APPOINTMENT,appointmentTime+"");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_appointment_time.setText(Tools.formatMinute(Long.parseLong(PreferencesHelper.getData(Constant.TIME_APPOINTMENT))));
                            }
                        });
                        if(appointmentTime<=0){
                            Log.e("yzh","cancel");
                            //预约超时
                            appointmentTimeOutDialog.show();
                            appointmentTimeOutDialog.setIvDeleteListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    appointmentTimeOutDialog.dismiss();
                                    finish();
                                }
                            });
                            appointmentTimeOutDialog.setReAppointment(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    appointmentTimeOutDialog.dismiss();
                                    finish();
                                }
                            });
//                        cancelTimerAppointment();
                        }
                    }
                }
            },1000,1000);
        }else{

        }

        map.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = map.getMap();
        }
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

//        aMap.addMarker(new MarkerOptions()
//                .position(Tools.convertToLatLng(mStartPoint)));
////                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)));
//        aMap.addMarker(new MarkerOptions()
//                .position(Tools.convertToLatLng(mEndPoint)));
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.home_ic_position)));


        MyLocationBean locationBean=PreferencesHelper.getData(MyLocationBean.class);
        if(locationBean!=null){
            mStartPoint=new LatLonPoint(locationBean.latitude,locationBean.longtitude);
        }
        mEndPoint=new LatLonPoint(endLatitude,endLongitude);
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
        aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(mStartPoint.getLatitude(),mEndPoint.getLongitude())));
        RxBus.getDefault().toObservable(MyLocationBean.class, Constant.REFRESH_LOCATION)
                .compose(this.<MyLocationBean>bindToLifecycle())
                .subscribe(new RxBusSubscriber<MyLocationBean>() {

                    @Override
                    public void receive(MyLocationBean data) {
                        //刷新坐标 位置不一致时才更新路线
                        if(mStartPoint!=null){
                            if(mStartPoint.getLatitude()!=data.latitude||mStartPoint.getLongitude()!=data.longtitude){
                                mStartPoint=new LatLonPoint(data.latitude,data.longtitude);
                                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
                            }
                        }

                    }
                });

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });
        RxBus.getDefault().toObservable(Object.class,Constant.REFRESH_APPOINTMENT_TIME)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        Log.e("yzh","guailds");
                        tv_appointment_time.setText(Tools.formatMinute(Long.parseLong(PreferencesHelper.getData(Constant.TIME_APPOINTMENT))));
                    }
                });
        RxBus.getDefault().toObservable(Object.class,Constant.APPOINTMENT_TIME_OUT)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        appointmentTimeOutDialog.show();
                        appointmentTimeOutDialog.setIvDeleteListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                appointmentTimeOutDialog.dismiss();
                                finish();
                            }
                        });
                        appointmentTimeOutDialog.setReAppointment(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                appointmentTimeOutDialog.dismiss();
                                finish();
                            }
                        });
                    }
                });
    }

    @OnClick(R.id.ll_time)
    public void hideOrShowInfo(){
        if(ll_hint.getVisibility()==View.VISIBLE){
            ll_hint.setVisibility(View.GONE);
        }else{
            ll_hint.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            ToastMgr.show( getString(R.string.guilding_get_location));
            return;
        }
        if (mEndPoint == null) {
            ToastMgr.show(getString(R.string.guilding_no_end));
            return;
        }
//        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery
                    query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }


    @Override
    protected GuaildPresenter createPresenter() {
        return new GuaildPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        if(timerAppointment!=null){
            timerAppointment=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            context, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(false);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    Log.e("yzh","dur"+"---"+dur+"---"+dis);

                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
//                    ToastUtil.show(mContext, R.string.no_result);
                }
            } else {
//                ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
//            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void cancelAppointmentSuccess() {
        //取消成功
        TimeServiceManager.getInstance().getTimerService().cancelTimerAppointment();
        HomeRefreshBean bean =new HomeRefreshBean();
        bean.type=1;
        RxBus.getDefault().send(bean,Constant.HOME_STATUE_REFRESH);
        finish();
    }

    private TimerService timerService;

    private ServiceConnection mConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.ServiceBinder binder =(TimerService.ServiceBinder)service;
            timerService=binder.getService();
            timerService.timeAppointment();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            timerService.cancelTimerAppointment();
            timerService=null;
        }
    };

    @Override
    public void goLogin() {

    }
}
