package com.isoftston.issuser.fusioncharge.adapters;

import android.content.Context;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.ElectronicPileBean;

/**
 * Created by issuser on 2018/4/19.
 */

public class HomeListAdpter extends QuickAdapter<ElectronicPileBean> {

    public HomeListAdpter(Context context){
        super(context, R.layout.item_home_list);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ElectronicPileBean item, int position) {

    }
}
