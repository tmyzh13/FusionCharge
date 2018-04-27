package com.isoftston.issuser.fusioncharge.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.LoginApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.RestPwdRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.UserBean;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.interfaces.LoginView;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/4/19.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    LoginApi api;

    @Override
    public void onStart() {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(LoginApi.class);
    }

    /**
     * @param type 0：手机号+密码 1：手机号+验证码 2：华为账号登录
     * @param
     * @param
     */
    public void loginAction(int type, final String phone, String pwd, String captcha) {

        LoginRequestBean bean = new LoginRequestBean();
        bean.phone = phone;
        bean.carrier = Tools.getPhoneType();
        bean.type = type;
        if (type == 0) {//密码登录
            bean.passWord = pwd;
        } else if (type == 1) {//验证码登录
            bean.captcha = captcha;
        }
        api.login(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserBean>>(view) {
                    @Override
                    public void success(BaseData<UserBean> baseData) {

                        Log.e("loginAction", "---code:" + baseData.code + ",token:" + baseData.data.token);
                        if (baseData.code == 0) {
                            PreferencesHelper.saveData(Constant.LOGIN_STATUE, "1");
                            PreferencesHelper.saveData("phone", phone);
                            UserHelper.saveUser(baseData.data);
                            if (null != PreferencesHelper.getData("phone")) {
                                PreferencesHelper.remove("phone");
                            }
                            if (null != PreferencesHelper.getData("token")) {
                                PreferencesHelper.remove("token");
                            }
                            PreferencesHelper.saveData("token", baseData.data.token);
                            view.loginSuccess();
                        } else {
                            view.loginFailure();
                        }
                    }
                });
    }

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param captcha
     */
    public void registerAction(String phone, String pwd, String captcha) {
        LoginRequestBean bean = new LoginRequestBean();
        bean.phone = phone;
        bean.passWord = pwd;
        bean.captcha = captcha;
        api.register(bean)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        if (baseData.code == 0) {
                            view.registerSuccess();
                        }
                    }
                });
    }

    /**
     * @param type  type:短信类型（1:获取注册短信验证码;2:获取找回密码短信,3:获取登录验证码）
     *              phone:手机号码
     * @param phone
     */
    public void getCodeAction(int type, final String phone) {
        LoginRequestBean bean = new LoginRequestBean();
        bean.type = type;
        bean.phone = phone;
        api.getCode(bean)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        PreferencesHelper.saveData("phone", phone);
                        view.getCodeSuccess();
                    }
                });
    }

    /**
     * 重置密码
     *
     * @param bean
     */
    public void restPwdAction(RestPwdRequestBean bean) {
        api.restPwd(bean)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        view.loginSuccess();
                    }
                });
    }
}
