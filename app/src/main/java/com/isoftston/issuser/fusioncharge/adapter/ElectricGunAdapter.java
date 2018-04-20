package com.isoftston.issuser.fusioncharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/19.
 */

public class ElectricGunAdapter extends BaseAdapter {
    private Context context;
    private List<ChargePileBean.ElectricGunBean> datas;
    private LayoutInflater mInflater;

    public ElectricGunAdapter(Context context,List<ChargePileBean.ElectricGunBean> datas) {
        this.context = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<ChargePileBean.ElectricGunBean> datas){
        this.datas = datas;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChargePileBean.ElectricGunBean gunBean = datas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_electric_gun_list, null);
            holder.gunNum = convertView.findViewById(R.id.electric_gun_num);
            holder.gunTypeIv = convertView.findViewById(R.id.gun_type_iv);
            holder.gunTypeTv = convertView.findViewById(R.id.gun_type_tv);
            holder.gunStatusIv = convertView.findViewById(R.id.gun_status_iv);
            holder.gunStatusTv = convertView.findViewById(R.id.gun_status_tv);
            holder.gunAppointmentTv = convertView.findViewById(R.id.gun_appointment_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.gunNum.setText(gunBean.getGunNum());
        return convertView;
    }

    public final class ViewHolder {
        public TextView gunNum;
        public ImageView gunTypeIv;
        public TextView gunTypeTv;

        public ImageView gunStatusIv;
        public TextView gunStatusTv;
        public TextView gunAppointmentTv;
    }
}
