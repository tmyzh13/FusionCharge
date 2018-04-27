package com.isoftston.issuser.fusioncharge.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.ChargePileBean;
import com.isoftston.issuser.fusioncharge.model.beans.ChargeStationDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.GunList;
import com.isoftston.issuser.fusioncharge.views.AppointmentChargeActivity;

import java.util.List;

/**
 * Created by issuser on 2018/4/19.
 */

public class ElectricGunAdapter extends BaseAdapter {
    private Context context;
    private List<GunList> datas;
    private LayoutInflater mInflater;
    private String pileName;

    private double latitude,longitude;

    public ElectricGunAdapter(Context context, List<GunList> datas,String pileName,double latitude,double longitude) {
        this.pileName = pileName;
        this.context = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<GunList> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
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
        final GunList gunBean = datas.get(position);
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

        //type=1  交流
        if (gunBean.getGunType() == 1) {
            holder.gunTypeIv.setImageResource(R.drawable.dots_green);
            holder.gunTypeTv.setText(R.string.statue_alternating_electronic);
        } else if (gunBean.getGunType() == 2) {
            holder.gunTypeIv.setImageResource(R.drawable.dots_yellow);
            holder.gunTypeTv.setText(R.string.statue_direct_electronic);
        }

        holder.gunAppointmentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AppointmentChargeActivity.class);
                intent.putExtra("gunCode",gunBean.getGunCode());
                intent.putExtra("chargingPileId",gunBean.getChargingPileId());
                intent.putExtra("chargingPileName",pileName);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                context.startActivity(intent);
            }
        });

        // 充电枪状态 1：空闲 2：使用中（插枪未充电） 3：使用中（已充电） 4：预约中 5：停止服务 6：故障
        if (gunBean.getGunStatus() == 1) {
            holder.gunStatusIv.setImageResource(R.drawable.dots_green);
            holder.gunStatusTv.setText(R.string.statue_free);
            holder.gunAppointmentTv.setClickable(true);
            holder.gunAppointmentTv.setTextColor(context.getResources().getColor(R.color.app_blue));
            holder.gunAppointmentTv.setBackgroundResource(R.drawable.blue_stroke_90angle_bg);
        } else if (gunBean.getGunStatus() == 2) {
            holder.gunStatusIv.setImageResource(R.drawable.dots_fault_red);
            holder.gunStatusTv.setText(R.string.useing_no_charge);
            holder.gunAppointmentTv.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.gunAppointmentTv.setBackgroundResource(R.drawable.appoint_gray_bg_shape);
            holder.gunAppointmentTv.setClickable(false);
        } else if (gunBean.getGunStatus() == 3) {
            holder.gunStatusIv.setImageResource(R.drawable.dots_yellow);
            holder.gunStatusTv.setText(R.string.useing);
            holder.gunAppointmentTv.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.gunAppointmentTv.setBackgroundResource(R.drawable.appoint_gray_bg_shape);
            holder.gunAppointmentTv.setClickable(false);
        } else if (gunBean.getGunStatus() == 4 ) {
            holder.gunStatusIv.setImageResource(R.drawable.dots_fault_red);
            holder.gunStatusTv.setText(R.string.appoingment_ing);
            holder.gunAppointmentTv.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.gunAppointmentTv.setBackgroundResource(R.drawable.appoint_gray_bg_shape);
            holder.gunAppointmentTv.setClickable(false);
        } else if (gunBean.getGunStatus() == 5 ) {
            holder.gunStatusIv.setImageResource(R.drawable.dots_fault_red);
            holder.gunStatusTv.setText(R.string.stop_server);
            holder.gunAppointmentTv.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.gunAppointmentTv.setBackgroundResource(R.drawable.appoint_gray_bg_shape);
            holder.gunAppointmentTv.setClickable(false);
        } else if (gunBean.getGunStatus() == 6 ) {
            holder.gunStatusIv.setImageResource(R.drawable.dots_fault_red);
            holder.gunStatusTv.setText(R.string.status_fault);
            holder.gunAppointmentTv.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.gunAppointmentTv.setBackgroundResource(R.drawable.appoint_gray_bg_shape);
            holder.gunAppointmentTv.setClickable(false);
        }
        holder.gunNum.setText(gunBean.getGunId() + "");

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
