package com.isoftston.issuser.fusioncharge.views.home;

import android.os.Bundle;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.ChargeFeeBean;
import com.isoftston.issuser.fusioncharge.weights.ChargeFeeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/19.
 */

public class MapFragment  extends BaseFragment{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.ll_fee)
    public void showFee(){
        ChargeFeeDialog dialog =new ChargeFeeDialog(getContext());
        dialog.show();
        List<ChargeFeeBean> list=new ArrayList<>();
        ChargeFeeBean bean=new ChargeFeeBean();
        bean.time="00:00~06:00";
        bean.unit="0.0030度";
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        dialog.setFeeDatas(list);

    }
}
