package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.fusioncharge.model.beans.HomeAppointmentBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeChargeOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.HomeOrderBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.PileFeeBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/23.
 */

public interface MapHomeView extends BaseView{

    void  renderMapData(List<MapDataBean> list);

    void getMarkInfo(long id,MapInfoBean bean);

    void showPileFeeInfo(PileFeeBean bean);

    void hasNoPayOrder(boolean has, HomeOrderBean bean);

    void renderAppoinmentInfo(HomeAppointmentBean bean);

    void renderHomeChargerOrder(HomeChargeOrderBean bean);
}
