package com.isoftston.issuser.fusioncharge.views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.corelibs.api.ApiFactory;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.apis.ScanApi;
import com.isoftston.issuser.fusioncharge.model.beans.ScanChargeInfo;
import com.isoftston.issuser.fusioncharge.weights.NavBar;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ChargeCaptureActivity extends BaseActivity {

    private ScanApi api;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.iv_light)
    ImageView ivLight;
    @Bind(R.id.ll_change_erweima_style)
    LinearLayout llChangeErweimaStyle;
    private CaptureManager capture;

    @Bind(R.id.dbv)
    DecoratedBarcodeView dbv;
    private Camera camera;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_capture;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(this.getString(R.string.charge));

        api = ApiFactory.getFactory().create(ScanApi.class);

        capture = new CaptureManager(this, dbv);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.setResultCallBack(new CaptureManager.ResultCallBack() {
            @Override
            public void callBack(int requestCode, int resultCode, Intent intent) {
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                if (null != result && null != result.getContents()) {
                    showLoadingDialog(result.getContents());
                }
            }
        });
        capture.decode();

    }

    private void showLoadingDialog(String contents){
        showLoading();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置屏幕长久亮屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        capture.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_DENIED) {
            capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(getLoadingDialog().isShowing()){
            return true;
        }
        return dbv.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    boolean isOpen = false;
    @OnClick({R.id.iv_light, R.id.ll_change_erweima_style})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_light:
                if(isOpen){
                    dbv.setTorchOff();
                    isOpen = false;
                } else {
                    dbv.setTorchOn();
                    isOpen = true;
                }
                break;
            case R.id.ll_change_erweima_style:
                startActivity(new Intent(this,ChargeInputNumberActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void goLogin() {

    }
}
