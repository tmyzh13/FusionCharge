package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Marker;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/24.
 */

public class ParkActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.mapview)
    MapView mapView;

    private AMap aMap;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,ParkActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_park;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.guilding_park_map));
        navBar.setImageBackground(R.drawable.nan_bg);

        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.iv_sao)
    public void actionScan(){

    }

    @Override
    public void goLogin() {

    }
}
