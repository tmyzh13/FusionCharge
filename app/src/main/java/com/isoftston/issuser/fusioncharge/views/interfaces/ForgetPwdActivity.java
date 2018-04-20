package com.isoftston.issuser.fusioncharge.views.interfaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.views.InputNewPwdActivity;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    private Context context = ForgetPwdActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.user_phone_num_tv)
    TextView userPhoneNumYv;
    @Bind(R.id.input_message_code_et)
    EditText inputMessageCodeEt;
    @Bind(R.id.not_received_code_tv)
    TextView notReceivedCodeTv;
    @Bind(R.id.get_message_code_tv)
    TextView getMessageCodeTv;
    @Bind(R.id.next_tv)
    TextView nextTv;


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
        notReceivedCodeTv.setOnClickListener(this);
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
                break;
            case R.id.next_tv:
                InputNewPwdActivity.getLaunch(context);
                break;
            default:
                break;
        }
    }
}
