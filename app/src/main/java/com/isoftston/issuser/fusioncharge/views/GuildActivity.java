package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps.MapView;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/23.
 */

public class GuildActivity  extends BaseActivity{

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.map)
    MapView map;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,GuildActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guaild;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.guilding));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
