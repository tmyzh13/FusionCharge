package com.isoftston.issuser.fusioncharge.views.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.beans.ChargeFeeBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.MyLocationBean;
import com.isoftston.issuser.fusioncharge.presenter.MapPresenter;
import com.isoftston.issuser.fusioncharge.views.ChagerStatueActivity;
import com.isoftston.issuser.fusioncharge.views.ChargeDetailsActivity;
import com.isoftston.issuser.fusioncharge.views.GuildActivity;
import com.isoftston.issuser.fusioncharge.views.interfaces.MapHomeView;
import com.isoftston.issuser.fusioncharge.weights.ChargeFeeDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/19.
 */

public class MapFragment  extends BaseFragment<MapHomeView,MapPresenter> implements MapHomeView, LocationSource, AMapLocationListener, AMap.OnMapTouchListener {

    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.rl_not_pay)
    RelativeLayout rl_not_pay;
    @Bind(R.id.rl_bottom_detail)
    RelativeLayout rl_bottom_detail;
    @Bind(R.id.ll_fee_all)
    LinearLayout ll_fee_all;
    @Bind(R.id.tv_map_info_name)
    TextView tv_map_info_name;
    @Bind(R.id.tv_map_info_score)
    TextView tv_map_info_score;
    @Bind(R.id.tv_map_info_address)
    TextView tv_map_info_address;
    @Bind(R.id.tv_map_info_distance)
    TextView tv_map_info_distance;
    @Bind(R.id.tv_map_info_pile_num)
    TextView tv_map_info_pile_num;
    @Bind(R.id.tv_map_info_gun_num)
    TextView tv_map_info_gun_num;
    @Bind(R.id.tv_map_info_free_num)
    TextView tv_map_info_free_num;
    @Bind(R.id.tv_map_info_current_fee)
    TextView tv_map_info_current_fee;
    @Bind(R.id.tv_map_info_service_fee)
    TextView tv_map_info_service_fee;

    private AMap aMap;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map.onCreate(savedInstanceState);
        aMap=map.getMap();

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                rl_bottom_detail.setVisibility(View.GONE);
            }
        });


        location();
        initMapData();
    }

    private  void initMapData(){


        presenter.getData();


    }


    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private void location(){
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        MyLocationStyle myLocationStyle=new MyLocationStyle();
        ImageView imageView=new ImageView(getContext());
        imageView.setImageResource(R.mipmap.location);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory

                .fromView(imageView);
        myLocationStyle.myLocationIcon(markerIcon);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle);
        //移动时 不让地图到定位点
        aMap.setOnMapTouchListener(this);
        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {

                MapDataBean bean =list.get(Integer.parseInt(marker.getTitle()));
                presenter.getInfo(bean.id,bean.type);
                return true;
            }
        };
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);

    }

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @OnClick(R.id.ll_fee)
    public void showFee(){
        ChargeFeeDialog dialog =new ChargeFeeDialog(getContext());
        dialog.show();
        List<ChargeFeeBean> list=new ArrayList<>();
        ChargeFeeBean bean=new ChargeFeeBean();
        bean.time="00:00~06:00";
        bean.unit="0.0030度";
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        dialog.setFeeDatas(list);

    }

    @OnClick(R.id.tv_pay)
    public void goPay(){
        startActivity(ChagerStatueActivity.getLauncher(getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        if (Build.VERSION.SDK_INT >= 23
                && getContext().getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions(needPermissions);
            }
        }
    }

    /**
     *
     * @param permissions
     * @since 2.5.0
     *
     */
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23
                    && getContext().getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                    Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class,
                            int.class});

                    method.invoke(this, array, PERMISSON_REQUESTCODE);
                }
            }
        } catch (Throwable e) {
        }
    }

    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= 23
                && getContext().getApplicationInfo().targetSdkVersion >= 23){
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
                            String.class);
                    if ((Integer)checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                            || (Boolean)shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                        needRequestPermissonList.add(perm);
                    }
                }
            } catch (Throwable e) {

            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否所有的权限都已经授权
     * @param grantResults
     * @return
     * @since 2.5.0
     *
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
//                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     *
     */
//    private void showMissingPermissionDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.notifyTitle);
//        builder.setMessage(R.string.notifyMsg);
//
//        // 拒绝, 退出应用
//        builder.setNegativeButton(R.string.cancel,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//
//        builder.setPositiveButton(R.string.setting,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startAppSettings();
//                    }
//                });
//
//        builder.setCancelable(false);
//
//        builder.show();
//    }

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
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(getContext());
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            mLocationOption.setInterval(2000);//设置间隔时间
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    private boolean followMove=true;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null&&aMapLocation != null) {
            if (aMapLocation != null
                    &&aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                MyLocationBean bean =new MyLocationBean();
                bean.latitude=aMapLocation.getLatitude();
                bean.longtitude=aMapLocation.getLongitude();
                PreferencesHelper.saveData(bean);
                if(followMove){
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude())));
                }
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private static final int PERMISSON_REQUESTCODE = 0;
    private boolean isNeedCheck = true;

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (followMove) {
            //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
            followMove = false;
        }
    }

    @OnClick(R.id.ll_go_to_detail)
    public void goDetail(){
        //进入对应的充电桩详情

    }

    private List<MapDataBean> list;

    @Override
    public void renderMapData(List<MapDataBean> list) {
        this.list=list;
        for(int i=0;i<list.size();i++){
            MarkerOptions markerOption = new MarkerOptions();
            LatLng latLng=new LatLng(list.get(i).latitude, list.get(i).longitude);
            markerOption.position(latLng);
            //点标记的标题和内容；

//            markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
            markerOption.title(i+"");
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.mipmap.home_ic_position)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            aMap.addMarker(markerOption);
        }
    }

    @Override
    public void getMarkInfo(MapInfoBean  mapInfoBean) {
        rl_bottom_detail.setVisibility(View.VISIBLE);
        if(mapInfoBean.objType.equals("station")){
            //充电站
        }else if(mapInfoBean.objType.equals("pile")){
            //充电桩
            ll_fee_all.setVisibility(View.GONE);
        }
        tv_map_info_name.setText(mapInfoBean.name);
        tv_map_info_address.setText(mapInfoBean.address);

    }

    @OnClick(R.id.iv_guaild)
    public void goGuaild(){
        startActivity(GuildActivity.getLauncher(getContext()));
    }

    @OnClick(R.id.enter_charge_station_iv)
    public void enterChargeStation() {
        startActivity(ChargeDetailsActivity.getLauncher(getActivity()));
    }
}
