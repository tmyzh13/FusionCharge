package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.fusioncharge.model.beans.PayInfoBean;

/**
 * Created by issuser on 2018/4/25.
 */

public interface PayView  extends BaseView{

    void renderData(PayInfoBean bean);
    void paySuccess();
    void payBalanceNotEnough();
}
