package com.isoftston.issuser.fusioncharge.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.CommentSortBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/26.
 */

public class CommentSortAdapter extends BaseAdapter {
    private List<CommentSortBean> data;
    private LayoutInflater mInflater;
    private boolean onlyType;
    public CommentSortAdapter(Context context, List<CommentSortBean> datas,boolean onlyType) {
        this.data = datas;
        this.onlyType = onlyType;
        mInflater = LayoutInflater.from(context);
    }
    public void setData(List<CommentSortBean> datas){
        this.data = datas;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comments_point, null, false);
        }
        convertView.setClickable(!onlyType);
        if (onlyType || data.get(position).getEvaluateCount() == 0) {
            ((TextView)convertView).setText(data.get(position).getTypeName());
        } else {
            ((TextView)convertView).setText(data.get(position).getTypeName() +"("
                    +data.get(position).getEvaluateCount()+")");
        }
        ((TextView)convertView).setGravity(Gravity.CENTER);
        return convertView;
    }
}
