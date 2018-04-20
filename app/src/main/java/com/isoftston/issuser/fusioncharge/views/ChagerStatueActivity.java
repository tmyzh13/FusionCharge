package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/20.
 */

public class ChagerStatueActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;

    private Context context=ChagerStatueActivity.this;
    private CommonDialog commonDialog;

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
                startActivity(PayActivity.getLauncher(context));
            }
        });
    }
}
