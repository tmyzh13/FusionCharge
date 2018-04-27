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

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapter.ChargePileTypeAdapter;
import com.isoftston.issuser.fusioncharge.adapter.ElectricGunAdapter;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.ScanApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileBean;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.ChargeStationDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestChargePileDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.ScanChargeInfo;
import com.isoftston.issuser.fusioncharge.weights.MyViewPager;
import com.isoftston.issuser.fusioncharge.weights.NavBar;
import com.trello.rxlifecycle.ActivityEvent;

import butterknife.Bind;
import butterknife.OnClick;

public class ChargeDetailsActivity extends BaseActivity {

    private static final String TAG = ChargeDetailsActivity.class.getSimpleName();
    private Context context = ChargeDetailsActivity.this;

    private final String STATION = "station";
    private final String PILE = "pile";

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
    private ScanApi api;
    private double latitude;
    private double longitude;
    private String id;

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
        api = ApiFactory.getFactory().create(ScanApi.class);

        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(context.getString(R.string.charging_pile_detail));

        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);
        id = getIntent().getStringExtra("id");

        getData();
    }

    private void getData(){
        showLoading();
        RequestChargePileDetailBean bean = new RequestChargePileDetailBean();

        bean.setId("1");
        bean.setType(STATION);
        api.getChargePileDetail(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<ChargeStationDetailBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<ChargeStationDetailBean>>() {
                    @Override
                    public void success(BaseData<ChargeStationDetailBean> baseData) {
                        Log.e("zw",TAG + " success : " + baseData.toString());
                        Log.e("zw",TAG + " success1 : " + baseData.data.toString());
                        initView(baseData.data);
                        hideLoading();
                    }

                    @Override
                    public boolean operationError(BaseData<ChargeStationDetailBean> baseData, int status, String message) {
                        Log.e("zw",TAG + " error : " + baseData.toString());
                        hideLoading();
                        showToast(getString(R.string.no_data));
                        finish();
                        return super.operationError(baseData, status, message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideLoading();
                        showToast(getString(R.string.wrong_request));
                        finish();
                    }
                });
    }

    private void initView(ChargeStationDetailBean bean){
        chargePileNameTv.setText(bean.getName());
        chargePileAddressTv.setText(bean.getAddress());
        scoreTv.setText(bean.getAverageScore() + "");
//        计算距离
        LatLng positionLatlng = new LatLng(latitude,longitude);
        LatLng userLatlng = new LatLng(bean.getLatitude(),bean.getLongitude());
        float distance = AMapUtils.calculateLineDistance(positionLatlng,userLatlng);
        Log.e("zw","distance : " + distance);

        mAdapter = new ChargePileTypeAdapter(getSupportFragmentManager(),bean);
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

    @Override
    public void goLogin() {

    }
}
