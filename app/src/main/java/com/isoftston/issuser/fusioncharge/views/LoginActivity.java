package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.presenter.LoginPresenter;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.interfaces.LoginView;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/18.
 */

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.phone_cut_line_ll)
    LinearLayout phoneCutView;
    @Bind(R.id.input_pwd_ll)
    LinearLayout inputPwdLl;
    @Bind(R.id.phone_et)
    EditText phoneNumberEt;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.type_code_ll)
    LinearLayout typeCodeLl;
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.get_code_tv)
    TextView getCodeTv;
    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.forget_pwd_register_rl)
    RelativeLayout forgetPwdandRegisterRl;
    @Bind(R.id.forget_pwd_tv)
    TextView forgetPwdTv;
    @Bind(R.id.register_tv)
    TextView registerTv;

    private Context context = LoginActivity.this;
    private String phoneNumber;
    private String code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        phoneCutView.setVisibility(View.GONE);
        inputPwdLl.setVisibility(View.GONE);
        forgetPwdandRegisterRl.setVisibility(View.GONE);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick(R.id.get_code_tv)
    public void getCode() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        if (Tools.isChinaPhoneLegal(phoneNumber)) {
            MyCountDownTimer timer = new MyCountDownTimer(60000, 1000);
            timer.start();
            ToastMgr.show(context.getString(R.string.reset_password_hint));
        } else {
            ToastMgr.show(context.getString(R.string.input_correct_phone));
        }
    }

    @OnClick(R.id.login_tv)
    public void loginAction() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        code = codeEt.getText().toString().trim();

//        if (true) {
//        presenter.loginAction(0, phoneNumber, code);
//        } else {
//            ToastMgr.show(context.getString(R.string.code_incorrect));
//            CommonDialog dialog = new CommonDialog(context,getResources().getString(R.string.code_error_hint), getResources().getString(R.string.code_incorrect), 1);
//            dialog.show();
//        }
        startActivity(ChargeDetailsActivity.getLauncher(context));
    }

    @Override
    public void loginSuccess() {
        //loginSuccess
//        startActivity(MainActivity.getLauncher(context));
    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCodeTv.setClickable(false);
            getCodeTv.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            getCodeTv.setClickable(true);
            getCodeTv.setText(R.string.action_get_code_again);
        }
    }
}
