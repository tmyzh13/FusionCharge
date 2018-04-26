package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.fusioncharge.model.beans.ChargerStatueBean;

/**
 * Created by issuser on 2018/4/24.
 */

public interface ChargerStatueView extends BaseView{

    void renderChargerStatueData(ChargerStatueBean bean);
    void endChargeSuccess();
    void endChargeFail();
}
