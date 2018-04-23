package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapter.ChargePileTypeAdapter;
import com.isoftston.issuser.fusioncharge.adapter.ElectricGunAdapter;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileBean;
import com.isoftston.issuser.fusioncharge.weights.MyViewPager;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

public class ChargeDetailsActivity extends BaseActivity {

    private static final String TAG = ChargeDetailsActivity.class.getSimpleName();
    private Context context = ChargeDetailsActivity.this;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.charge_pile_name_tv)
    TextView chargePileNameTv;
    @Bind(R.id.score_tv)
    TextView scoreTv;
    @Bind(R.id.charge_pile_address_tv)
    TextView chargePileAddressTv;
    @Bind(R.id.distance_tv)
    TextView distanceTv;
    @Bind(R.id.picture_tv)
    TextView pictureTv;
    @Bind(R.id.position_tv)
    TextView positionTv;
    @Bind(R.id.comments_tv)
    TextView commentsTv;
    @Bind(R.id.my_view_pager)
    MyViewPager myViewPager;
    ChargePileTypeAdapter mAdapter;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, ChargeDetailsActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(context.getString(R.string.charging_pile_detail));
        mAdapter = new ChargePileTypeAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(mAdapter);
        chosePicture();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.picture_tv)
    public void choicePicture() {
        chosePicture();
    }

    @OnClick(R.id.position_tv)
    public void choicePosition() {
        chosePosition();
    }

    @OnClick(R.id.comments_tv)
    public void choiceComments() {
        choseComments();
    }

    private void chosePicture() {
        pictureTv.setTextColor(getResources().getColor(R.color.app_blue));
        positionTv.setTextColor(getResources().getColor(R.color.text_main));
        commentsTv.setTextColor(getResources().getColor(R.color.text_main));
        myViewPager.setCurrentItem(0);
    }

    private void chosePosition() {
        positionTv.setTextColor(getResources().getColor(R.color.app_blue));
        pictureTv.setTextColor(getResources().getColor(R.color.text_main));
        commentsTv.setTextColor(getResources().getColor(R.color.text_main));
        myViewPager.setCurrentItem(1);
    }

    private void choseComments() {
        commentsTv.setTextColor(getResources().getColor(R.color.app_blue));
        positionTv.setTextColor(getResources().getColor(R.color.text_main));
        pictureTv.setTextColor(getResources().getColor(R.color.text_main));
        myViewPager.setCurrentItem(2);
    }
}
