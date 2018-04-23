package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapInfoBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/23.
 */

public interface MapHomeView extends BaseView{

    void  renderMapData(List<MapDataBean> list);

    void getMarkInfo(MapInfoBean bean);
}
