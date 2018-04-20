package com.isoftston.issuser.fusioncharge.presenter;

import android.text.TextUtils;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.apis.LoginApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
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
     *
     * @param type 0：手机号+密码 1：手机号+验证码 2：华为账号登录
     * @param phoneNum
     * @param code
     */
    public void loginAction(int type, String phoneNum, String code) {

        if (checkLoginInput(phoneNum, code)) {
            LoginRequestBean bean = new LoginRequestBean();
            bean.phoneNumber = phoneNum;
            bean.code = code;
            api.login(bean)
                    .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                    .subscribe(new ResponseSubscriber<BaseData>(view) {
                        @Override
                        public void success(BaseData baseData) {
                            view.loginSuccess();
                        }
                    });
        }
    }

    private boolean checkLoginInput(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            ToastMgr.show(getContext().getString(R.string.hint_input_phone));
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            ToastMgr.show(getContext().getString(R.string.hint_input_password));
            return false;
        }

        return true;
    }
}
