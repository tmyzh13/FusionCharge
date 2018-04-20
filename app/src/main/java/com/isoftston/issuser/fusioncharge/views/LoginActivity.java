package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.presenter.LoginPresenter;
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
                    break;
                case 2://员工账号登录
                    break;
                case 3://验证码登录
                    phoneDropDownIv.setVisibility(View.GONE);
                    phoneCutLine.setVisibility(View.INVISIBLE);
                    inputPwdLl.setVisibility(View.GONE);
                    forgetPwdandRegisterRl.setVisibility(View.GONE);
                    typeCodeLl.setVisibility(View.VISIBLE);
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
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick(R.id.get_code_tv)
    public void getCode() {
        getUserInput();
        if (Tools.isChinaPhoneLegal(phoneNumber)) {
            MyCountDownTimer timer = new MyCountDownTimer(60000, 1000);
            timer.start();
            ToastMgr.show(context.getString(R.string.reset_password_hint));
        } else {
            ToastMgr.show(context.getString(R.string.input_correct_phone));
        }
    }

    private void getUserInput() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        code = codeEt.getText().toString().trim();
        pwd = pwdEt.getText().toString().trim();
    }

    @Override
    public void loginSuccess() {
        //loginSuccess
//        startActivity(MainActivity.getLauncher(context));
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
            case R.id.login_tv:
                startActivity(ChargeDetailsActivity.getLauncher(context));
                if (Tools.isPwdRight(pwdEt.getText().toString().trim())) {
                    Log.e(TAG, "----密码合规," + pwdEt.getText().toString().trim());
                } else {
                    Log.e(TAG, "----密码不合规xxx:" + pwdEt.getText().toString().trim());
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
