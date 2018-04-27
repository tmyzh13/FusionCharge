package com.isoftston.issuser.fusioncharge.views;

import android.os.Bundle;
import android.os.Handler;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;

/**
 * Created by issuser on 2018/4/27.
 */

public class WelcomeActivity extends BaseActivity {
    @Override
    public void goLogin() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                startActivity(MainActivity.getLauncher(WelcomeActivity.this));
            }
        }, 2000);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
