package com.isoftston.issuser.fusioncharge.views.fragments;


import android.os.Bundle;
import android.widget.ListView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapter.ChargePileAdapter;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileBean;
import com.isoftston.issuser.fusioncharge.model.beans.ChargeStationDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.PileList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class PositionFragment extends BaseFragment {
    private static final String TAG = PositionFragment.class.getSimpleName();

    public ChargeStationDetailBean data;

    @Bind(R.id.charge_pile_Lv)
    ListView chargePileLv;
    private ChargePileAdapter chargePileAdapter;
//    private List<ChargePileBean.ElectricGunBean> gunList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_position;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        chargePileAdapter = new ChargePileAdapter(getActivity(), data);
        chargePileLv.setAdapter(chargePileAdapter);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void goLogin() {

    }
}
