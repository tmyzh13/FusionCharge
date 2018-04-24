package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.beans.MyLocationBean;
import com.isoftston.issuser.fusioncharge.utils.DrivingRouteOverLay;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/23.
 */

public class GuildActivity  extends BaseActivity implements RouteSearch.OnRouteSearchListener {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.map)
    MapView map;

    private AMap aMap;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private final int ROUTE_TYPE_DRIVE = 2;

    private LatLonPoint mStartPoint = null;//起点，116.335891,39.942295
    private LatLonPoint mEndPoint = null;//终点，116.481288,39.995576
    private Context context=GuildActivity.this;
    private DrivingRouteOverLay drivingRouteOverlay;
    private double endLatitude,endLongitude;

    public static Intent getLauncher(Context context,double latitude,double longitude){
        Intent intent =new Intent(context,GuildActivity.class);
        intent.putExtra("la",latitude);
        intent.putExtra("ln",longitude);
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

        endLatitude=getIntent().getDoubleExtra("la",0);
        endLongitude=getIntent().getDoubleExtra("ln",0);

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
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();

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
}
