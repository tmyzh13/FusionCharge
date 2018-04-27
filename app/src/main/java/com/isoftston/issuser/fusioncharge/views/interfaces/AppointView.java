package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.fusioncharge.model.beans.AppointResponseBean;

/**
 * Created by issuser on 2018/4/23.
 */

public interface AppointView extends BaseView{
    void appointSuccess(AppointResponseBean bean);
}
