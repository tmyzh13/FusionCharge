package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.SharedPreferencesClassHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
import com.isoftston.issuser.fusioncharge.presenter.LoginPresenter;
import com.isoftston.issuser.fusioncharge.utils.MD5Utils;
import com.isoftston.issuser.fusioncharge.utils.SharePrefsUtils;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.interfaces.ForgetPwdActivity;
import com.isoftston.issuser.fusioncharge.views.interfaces.LoginView;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/18.
 */

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.phone_iv)
    ImageView phoneIv;
    @Bind(R.id.phone_dropdown_iv)
    ImageView phoneDropDownIv;
    @Bind(R.id.phone_cut_line)
    View phoneCutLine;
    @Bind(R.id.phone_et)
    EditText phoneNumberEt;
    @Bind(R.id.input_pwd_ll)
    LinearLayout inputPwdLl;//密码登录ui
    @Bind(R.id.lock_cut_line)
    View lockCutLine;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.type_code_ll)
    LinearLayout typeCodeLl;//验证码登录ui
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
    private String pwd;
    private String code;
    private PopupWindow popupWindow;
    private TextView phoneTypeTv;
    private TextView workerTypeTv;
    private TextView codeTypeTv;
    private int type = 1;//登录方式

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://手机号登录
                    phoneDropDownIv.setVisibility(View.GONE);
                    phoneCutLine.setVisibility(View.INVISIBLE);
                    inputPwdLl.setVisibility(View.VISIBLE);
                    lockCutLine.setVisibility(View.GONE);
                    typeCodeLl.setVisibility(View.GONE);
                    forgetPwdandRegisterRl.setVisibility(View.VISIBLE);
                    loginTv.setText(getText(R.string.login));
                    break;
                case 2://员工账号登录
                    loginTv.setText(getText(R.string.login));
                    break;
                case 3://验证码登录
                    phoneDropDownIv.setVisibility(View.GONE);
                    phoneCutLine.setVisibility(View.INVISIBLE);
                    inputPwdLl.setVisibility(View.GONE);
                    forgetPwdandRegisterRl.setVisibility(View.GONE);
                    typeCodeLl.setVisibility(View.VISIBLE);
                    loginTv.setText(getText(R.string.login));
                    break;
                case 4://忘记密码
                    startActivity(ForgetPwdActivity.getLaunch(context));
                    break;
                case 5://注册账号
                    phoneDropDownIv.setVisibility(View.GONE);
                    phoneCutLine.setVisibility(View.INVISIBLE);
                    lockCutLine.setVisibility(View.GONE);
                    inputPwdLl.setVisibility(View.VISIBLE);
                    typeCodeLl.setVisibility(View.VISIBLE);
                    forgetPwdandRegisterRl.setVisibility(View.GONE);
                    loginTv.setText(getText(R.string.regist));
                    registerUser();
                    break;
            }
        }
    };

    /**
     * 用户注册
     */
    private void registerUser() {
        getUserInput();
        code = codeEt.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastMgr.show(getResources().getText(R.string.hint_input_code));
            return;
        }
        //调用注册接口
        presenter.registerAction(phoneNumber, pwd, code);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        typeCodeLl.setVisibility(View.GONE);
        phoneIv.setOnClickListener(this);
        forgetPwdTv.setOnClickListener(this);
        registerTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        getCodeTv.setOnClickListener(this);
        phoneNumber = SharePrefsUtils.getValue(context, "phone", "1");
        if (!phoneNumber.equals("1")) {
            phoneNumberEt.setText(phoneNumber);
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    public void getCode() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        if (Tools.isChinaPhoneLegal(phoneNumber)) {
            MyCountDownTimer timer = new MyCountDownTimer(getCodeTv,60000, 1000);
            timer.start();
            ToastMgr.show(context.getString(R.string.reset_password_hint));
        } else {
            ToastMgr.show(context.getString(R.string.input_correct_phone));
            return;
        }
        if (type == 1) {
            Log.e(TAG, "---获取注册验证码");
            presenter.getCodeAction(type, phoneNumber);
        } else if (type == 3) {
            Log.e(TAG, "---获取登录验证码");
            presenter.getCodeAction(3, phoneNumber);
        }
    }

    private void getUserInput() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        pwd = pwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber) || !Tools.isChinaPhoneLegal(phoneNumber)) {
            ToastMgr.show(getResources().getText(R.string.input_correct_phone));
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastMgr.show(getResources().getText(R.string.hint_input_password));
            return;
        }
        if (!Tools.isPwdRight(pwd)) {
            ToastMgr.show(getResources().getText(R.string.regist_password_hint));
            return;
        }
    }

    @Override
    public void loginSuccess() {
        //loginSuccess
//        SharePrefsUtils.putValue(context,"token",data);
        RxBus.getDefault().send(new Object(), Constant.REFRESH_HOME_STATUE);
        finish();
//        startActivity(MainActivity.getLauncher(context));
    }

    @Override
    public void registerSuccess(String result) {
        Log.e(TAG, "----registerSuccess:" + result);
        //login
    }

    @Override
    public void registerFailure(String result) {

    }

    @Override
    public void getCodeSuccess(String result) {
        Log.e(TAG, "----result:" + result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_iv:
                showPopuWindow(v);
                break;
            case R.id.forget_pwd_tv:
                handler.sendEmptyMessage(4);
                break;
            case R.id.register_tv:
                Log.e("yzh","register_tv");
                handler.sendEmptyMessage(5);
                break;
            case R.id.get_code_tv:
                getCode();
                break;
            case R.id.login_tv:
//                startActivity(ChargeDetailsActivity.getLauncher(context));
                if (type == 1) {
                    getUserInput();
                    //手机号密码登录
                    presenter.loginAction(0, phoneNumber, pwd, code);
                    Log.e(TAG, "----手机号密码登录"+",pwd:"+MD5Utils.encode(pwd));

                } else if (type == 3) {
                    code = codeEt.getText().toString().trim();
                    if (TextUtils.isEmpty(code)) {
                        ToastMgr.show(getResources().getText(R.string.hint_input_code));
                        return;
                    }
                    //手机号验证码登录
                    presenter.loginAction(1, phoneNumber, MD5Utils.encode(pwd), code);
                    Log.e(TAG, "----手机号+验证码登录"+",pwd:"+MD5Utils.encode(pwd));
                }
                break;
            case R.id.pwd_et:
                ToastMgr.show(getResources().getText(R.string.regist_password_hint));
                break;
            default:
                break;
        }
    }

    /**
     * 登录方式选择
     *
     * @param v
     */
    private void showPopuWindow(View v) {
        popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.title_popuwindow, null);
        phoneTypeTv = view.findViewById(R.id.phone_login_tv);
        workerTypeTv = view.findViewById(R.id.worker_login_tv);
        codeTypeTv = view.findViewById(R.id.code_login_tv);
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(v);
        getLoginType();
    }

    private void getLoginType() {
        phoneTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                handler.sendEmptyMessage(type);

                popupWindow.dismiss();
            }
        });
        workerTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                handler.sendEmptyMessage(type);
                popupWindow.dismiss();
            }
        });
        codeTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                handler.sendEmptyMessage(type);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void goLogin() {

    }


    public static class MyCountDownTimer extends CountDownTimer {
        TextView view;
        public MyCountDownTimer(TextView v,long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.view = v;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            view.setClickable(false);
            view.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            view.setClickable(true);
            view.setText(R.string.action_get_code_again);
        }
    }
}
