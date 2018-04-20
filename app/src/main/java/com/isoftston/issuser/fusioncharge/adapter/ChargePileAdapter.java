package com.isoftston.issuser.fusioncharge.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 充电桩 列表adapter
 * Created by issuser on 2018/4/19.
 */

public class ChargePileAdapter extends BaseAdapter {
    private Context context;
    private List<ChargePileBean> datas;
    private LayoutInflater mInflater;

    public ChargePileAdapter(Context context, List<ChargePileBean> datas) {
        this.context = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    List<ChargePileBean.ElectricGunBean> gunList = new ArrayList<>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChargePileBean chargePileBean = datas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_charge_pile_list, null);
            holder.pileStatusIv = convertView.findViewById(R.id.pile_status_iv);
            holder.pileStatusTv = convertView.findViewById(R.id.pile_status_tv);
            holder.pileNumTv = convertView.findViewById(R.id.pile_num_tv);
            holder.maxPowerTv = convertView.findViewById(R.id.max_power_tv);
            holder.maxElectronicTv = convertView.findViewById(R.id.max_electronic_tv);
            holder.maxVoltageTv = convertView.findViewById(R.id.max_voltage_tv);
            holder.electricGunLv = convertView.findViewById(R.id.electric_gun_lv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pileNumTv.setText(chargePileBean.getPileNum());
        holder.maxPowerTv.setText(chargePileBean.getMaxPower());
        holder.maxElectronicTv.setText(chargePileBean.getMaxElectric());
        holder.maxVoltageTv.setText(chargePileBean.getMaxVoltage());

        gunList = chargePileBean.getGunBeanList();
        ElectricGunAdapter adapter = new ElectricGunAdapter(context,gunList);
        Log.e("dp","---size="+gunList.size());
        holder.electricGunLv.setAdapter(adapter);
//        adapter.setData(gunList);
        setListViewHeight(holder.electricGunLv);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView pileStatusIv;
        public TextView pileStatusTv;

        public TextView pileNumTv;
        public TextView maxPowerTv;
        public TextView maxElectronicTv;
        public TextView maxVoltageTv;
        ListView electricGunLv;
    }

    /**
     * 设置内部Listview的高度
     */
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
