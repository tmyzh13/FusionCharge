package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

public class InputNewPwdActivity extends BaseActivity {

    private Context context = InputNewPwdActivity.this;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_new_pwd_et)
    EditText inputNewPwdEt;
    @Bind(R.id.confirm_pwd_et)
    EditText confirmPwdEt;

    private String newPwd1;
    private String newPwd2;

    public static Intent getLaunch(Context context) {
        Intent intent = new Intent(context, InputNewPwdActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_new_pwd;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getResources().getString(R.string.reset_password_input_password));
        navBar.setColor(getResources().getColor(R.color.app_blue));
    }

    @OnClick(R.id.submit_tv)
    public void actionSubmit() {
        //判断密码是否符合要求及两次是否一致
        newPwd1 = inputNewPwdEt.getText().toString().trim();
        newPwd2 = confirmPwdEt.getText().toString().trim();
        if (!TextUtils.isEmpty(newPwd1) && !TextUtils.isEmpty(newPwd2) && newPwd1.equals(newPwd2)) {
            //todo
            //提交...
            finish();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
