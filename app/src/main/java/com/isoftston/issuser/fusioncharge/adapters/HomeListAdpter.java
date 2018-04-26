package com.isoftston.issuser.fusioncharge.adapters;

import android.content.Context;
import android.widget.TextView;

import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.beans.ElectronicPileBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MyLocationBean;
import com.isoftston.issuser.fusioncharge.utils.Tools;

/**
 * Created by issuser on 2018/4/19.
 */

public class HomeListAdpter extends QuickAdapter<MapDataBean> {


    public HomeListAdpter(Context context){
        super(context, R.layout.item_home_list);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, MapDataBean item, int position) {
        helper.setText(R.id.tv_name,item.title)
                .setText(R.id.tv_address,item.address)
                .setText(R.id.tv_direct,item.directNum+"")
                .setText(R.id.tv_alter,item.alterNum+"");
        TextView tv_distance=helper.getView(R.id.tv_distance);

        tv_distance.setText(item.distance+"KM");

    }
}
