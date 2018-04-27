package com.isoftston.issuser.fusioncharge.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.utils.alipay.CachedSearchTitleUtils;
import com.isoftston.issuser.fusioncharge.views.SearchStationTitleActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issuser on 2018/4/27.
 */

public class SearchHistoryOrResultAdapter extends BaseAdapter {
    private List<String> historyData = new ArrayList<>();
    private List<MapDataBean> resultData = new ArrayList<>();
    private boolean isHistoryData = true;
    private Context context;

    public void setResultData(List<MapDataBean> bean){
        resultData = bean;
        isHistoryData = false;
    }

    public void setHistoryType(){
        isHistoryData = true;
    }

    public Bundle getIntent(int position){
        Bundle bd = new Bundle();
        if (isHistoryData) {
            bd.putLong(SearchStationTitleActivity.KEY_ID,CachedSearchTitleUtils.getCachedData(position-1).id);
            bd.putString(SearchStationTitleActivity.KEY_TYPE,CachedSearchTitleUtils.getCachedData(position-1).type);
            bd.putString(SearchStationTitleActivity.KEY_TITLE,CachedSearchTitleUtils.getCachedData(position-1).station);
        } else {
            bd.putLong(SearchStationTitleActivity.KEY_ID,resultData.get(position-1).id);
            bd.putString(SearchStationTitleActivity.KEY_TYPE,resultData.get(position-1).type);
            bd.putString(SearchStationTitleActivity.KEY_TITLE,resultData.get(position-1).title);
        }
        return bd;
    }

    public SearchHistoryOrResultAdapter(Context context) {
        this.context = context;
        isHistoryData = true;
    }

    public void  resetShowHistoryData(){
        historyData = CachedSearchTitleUtils.getHistoryData();
        isHistoryData = true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return isHistoryData ? (historyData.size()+1) : (resultData.size()+1);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_history_or_result,null);
            holder = new ViewHolder();
            holder.history = (TextView) convertView.findViewById(R.id.history);
            holder.linear = (LinearLayout)convertView.findViewById(R.id.result);
            holder.title = (TextView) convertView.findViewById(R.id.result_title);
            holder.adress = (TextView) convertView.findViewById(R.id.resut_adress);
            holder.goDetail = (ImageView) convertView.findViewById(R.id.go_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isHistoryData){
            holder.linear.setVisibility(View.GONE);
            holder.goDetail.setVisibility(View.GONE);
            holder.history.setVisibility(View.VISIBLE);
            if (position == 0){
                holder.history.setText(R.string.search_history);
                //holder.history.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                holder.history.setTextColor(context.getResources().getColor(R.color.black));
            } else {
                holder.history.setTextColor(context.getResources().getColor(R.color.text_gray));
                holder.history.setText(historyData.get(position-1));
                //holder.history.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            }
        } else {
            holder.linear.setVisibility(View.VISIBLE);
            holder.history.setVisibility(View.GONE);
            if (position == 0) {
               holder.title.setVisibility(View.GONE);
               holder.goDetail.setVisibility(View.GONE);
               holder.adress.setText(R.string.search_station_destation);
            } else {
                holder.goDetail.setVisibility(View.VISIBLE);
                if (holder.title.getVisibility() != View.VISIBLE) {
                    holder.title.setVisibility(View.VISIBLE);
                }
                holder.title.setText(resultData.get(position-1).title);
                holder.adress.setText(resultData.get(position-1).address);
            }
        }
        return convertView;
    }

    public final class ViewHolder{
        public TextView history,title,adress;
        public ImageView goDetail;
        public LinearLayout linear;

    }
}
