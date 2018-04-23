package com.isoftston.issuser.fusioncharge.views.fragments;


import android.os.Bundle;
import android.widget.ListView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapter.ChargePileAdapter;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class PositionFragment extends BaseFragment {
    private static final String TAG = PositionFragment.class.getSimpleName();

    @Bind(R.id.charge_pile_Lv)
    ListView chargePileLv;
    private ChargePileAdapter chargePileAdapter;
    private List<ChargePileBean> chargePileList;
//    private List<ChargePileBean.ElectricGunBean> gunList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_position;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        chargePileList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ChargePileBean pileBean = new ChargePileBean();
            pileBean.setPileNum(321 + "i:"+i);
            pileBean.setMaxPower(220 + i + "KW");
            pileBean.setMaxElectric(110 + i + "A");
            pileBean.setMaxVoltage(120 + i + "V");

            List<ChargePileBean.ElectricGunBean> gunList = new ArrayList<>();
            for (int j = 0; j <3 ; j++) {
                ChargePileBean.ElectricGunBean gunBean = new ChargePileBean.ElectricGunBean();
                gunBean.setGunNum(j + "00" + j);
                gunBean.setType("交流");
                gunBean.setStatus("空闲");
                gunList.add(gunBean);
            }
            pileBean.setGunBeanList(gunList);
            chargePileList.add(pileBean);
        }
        chargePileAdapter = new ChargePileAdapter(getActivity(), chargePileList);
        chargePileLv.setAdapter(chargePileAdapter);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
