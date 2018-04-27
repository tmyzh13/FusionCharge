package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
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
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.presenter.LoginPresenter;
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

    private boolean isRegister = false;

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
                    isRegister = false;
                    break;
                case 2://员工账号登录
                    loginTv.setText(getText(R.string.login));
                    isRegister = false;
                    break;
                case 3://验证码登录
                    phoneDropDownIv.setVisibility(View.GONE);
                    phoneCutLine.setVisibility(View.INVISIBLE);
                    inputPwdLl.setVisibility(View.GONE);
                    forgetPwdandRegisterRl.setVisibility(View.GONE);
                    typeCodeLl.setVisibility(View.VISIBLE);
                    loginTv.setText(getText(R.string.login));
                    isRegister = false;
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
                    isRegister = true;
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
            showHintDialog(getString(R.string.hint),getString(R.string.hint_input_code));
            return;
        }
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
        if (null !=PreferencesHelper.getData("phone")) {
            phoneNumber = PreferencesHelper.getData("phone");
            phoneNumberEt.setText(phoneNumber);
            phoneNumberEt.setSelection(phoneNumber.length());
        }
        final PopupWindow popupWindow;
        popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pwd_hint, null);
        popupWindow.setContentView(view);

        pwdEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    popupWindow.showAsDropDown(v);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.dismiss();
                        }
                    },1000);
                }
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    public void getCode() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        if (Tools.isChinaPhoneLegal(phoneNumber)) {
            MyCountDownTimer timer = new MyCountDownTimer(getCodeTv, 60000, 1000);
            timer.start();
        } else {
            showHintDialog(getString(R.string.hint),getString(R.string.input_correct_phone));
            return;
        }
        if (type == 1) {
            presenter.getCodeAction(type, phoneNumber);
        } else if (type == 3) {
            presenter.getCodeAction(3, phoneNumber);
        }
    }

    private void showHintDialog(String title,String meg) {
        CommonDialog dialog = new CommonDialog(context,title,meg,1);
        dialog.show();
    }

    private void getUserInput() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        pwd = pwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber) || !Tools.isChinaPhoneLegal(phoneNumber)) {
            showHintDialog(getString(R.string.hint),getString(R.string.input_correct_phone));
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            showHintDialog(getString(R.string.hint),getString(R.string.hint_input_password));
            return;
        } else if (!Tools.isPwdRight(pwd)) {
            showHintDialog(getString(R.string.hint),getString(R.string.regist_password_hint));
            return;
        }
    }

    @Override
    public void loginSuccess() {
        RxBus.getDefault().send(new Object(), Constant.REFRESH_HOME_STATUE);
        startActivity(MainActivity.getLauncher(context));
        finish();
    }

    @Override
    public void loginFailure() {
        showHintDialog(getString(R.string.hint),getString(R.string.account_pwd_fault));
    }

    @Override
    public void registerSuccess() {
        handler.sendEmptyMessage(1);
    }

    @Override
    public void registerFailure() {

    }

    @Override
    public void getCodeSuccess() {
        Log.e(TAG, "----getCodeSuccess:");
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
                handler.sendEmptyMessage(5);
                break;
            case R.id.get_code_tv:
                getCode();
                break;
            case R.id.login_tv:
                if (!isRegister) {
                    if (type == 1) {
                        getUserInput();
                        //手机号密码登录
                        presenter.loginAction(0, phoneNumber, pwd, code);
                    } else if (type == 3) {
                        code = codeEt.getText().toString().trim();
                        if (TextUtils.isEmpty(code)) {
                            showHintDialog(getString(R.string.hint),getString(R.string.hint_input_code));
                            return;
                        }
                        //手机号验证码登录
                        presenter.loginAction(1, phoneNumber, pwd, code);
                    }
                }else {
                    //调用注册接口
                    registerUser();
                    presenter.registerAction(phoneNumber, pwd, code);
                }
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

        public MyCountDownTimer(TextView v, long millisInFuture, long countDownInterval) {
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
