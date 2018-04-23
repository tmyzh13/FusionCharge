package com.isoftston.issuser.fusioncharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.CommentsBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/23.
 */

public class CommentsAdapter extends BaseAdapter {
    private List<CommentsBean> datas;
    private LayoutInflater mInflater;

    public CommentsAdapter(Context context, List<CommentsBean> datas) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentsBean bean = datas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_comments_list, null);
            holder.headIv = convertView.findViewById(R.id.commenter_head_iv);
            holder.name = convertView.findViewById(R.id.commenter_name_tv);
            holder.time = convertView.findViewById(R.id.commenter_time_tv);
            holder.content = convertView.findViewById(R.id.commenter_content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(bean.getName());
        holder.time.setText(bean.getTime());
        holder.content.setText(bean.getContent());

        return convertView;
    }

    public class ViewHolder {
        private ImageView headIv;
        private TextView name;
        private TextView time;
        private TextView content;
    }
}
