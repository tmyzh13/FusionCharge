package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;

/**
 * Created by issuser on 2018/4/19.
 */

public interface LoginView extends BaseView{
    void loginSuccess();
    void registerSuccess(String result);
    void registerFailure(String result);
    void getCodeSuccess(String result);
}
