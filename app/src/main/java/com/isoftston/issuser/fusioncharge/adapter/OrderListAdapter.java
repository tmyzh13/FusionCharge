package com.isoftston.issuser.fusioncharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.OrderBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/27.
 */

public class OrderListAdapter extends BaseAdapter {
    private List<OrderBean> list;
    private LayoutInflater inflater;

    public OrderListAdapter(Context context, List<OrderBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_order_lv_layout,null);
            holder.oder_code_tv = convertView.findViewById(R.id.oder_code_tv);
            holder.oder_time_tv = convertView.findViewById(R.id.oder_time_tv);
            holder.charge_pile_code = convertView.findViewById(R.id.charge_pile_code);
            holder.charge_pile_address = convertView.findViewById(R.id.charge_pile_address);
            holder.oder_status_tv = convertView.findViewById(R.id.oder_status_tv);
            holder.oder_charge_fee_tv = convertView.findViewById(R.id.oder_charge_fee_tv);
            holder.oder_service_fee_tv = convertView.findViewById(R.id.oder_service_fee_tv);
            holder.oder_total_fee_tv = convertView.findViewById(R.id.oder_total_fee_tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据

        return convertView;
    }

    public class ViewHolder{
        TextView oder_code_tv;
        TextView oder_time_tv;
        TextView charge_pile_code;
        TextView charge_pile_address;
        TextView oder_status_tv;
        TextView oder_charge_fee_tv;
        TextView oder_service_fee_tv;
        TextView oder_total_fee_tv;
    }
}
