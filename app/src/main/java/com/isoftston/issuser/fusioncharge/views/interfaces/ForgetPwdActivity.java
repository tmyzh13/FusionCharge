package com.isoftston.issuser.fusioncharge.views.interfaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.InputNewPwdActivity;
import com.isoftston.issuser.fusioncharge.views.LoginActivity;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    private Context context = ForgetPwdActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_your_phone_et)
    EditText phoneNumEt;
    @Bind(R.id.input_message_code_et)
    EditText inputMessageCodeEt;
    @Bind(R.id.get_message_code_tv)
    TextView getMessageCodeTv;
    @Bind(R.id.next_tv)
    TextView nextTv;

    private String phoneNum;
    private String code;

    public static Intent getLaunch(Context context) {
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getResources().getString(R.string.reset_password));
        navBar.setColor(getResources().getColor(R.color.app_blue));
        clicks();
    }

    private void clicks() {
        getMessageCodeTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.not_received_code_tv:
                break;
            case R.id.get_message_code_tv:
                phoneNum = phoneNumEt.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNum) && Tools.isChinaPhoneLegal(phoneNum)) {
                    LoginActivity.MyCountDownTimer timer = new LoginActivity.MyCountDownTimer(getMessageCodeTv, 600000, 1000);
                    timer.start();
                } else {
                    ToastMgr.show(R.string.input_correct_phone);
                    return;
                }
                break;
            case R.id.next_tv:
                phoneNum = phoneNumEt.getText().toString().trim();
                code = inputMessageCodeEt.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(code)) {
                    InputNewPwdActivity.getLaunch(context);
                } else {
                    ToastMgr.show(R.string.hint_input_code);
                    return;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void goLogin() {

    }
}
