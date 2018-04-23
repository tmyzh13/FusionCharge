package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/23.
 */

public interface HomeListView extends BaseView {

    void rendData(List<MapDataBean> list);
}
