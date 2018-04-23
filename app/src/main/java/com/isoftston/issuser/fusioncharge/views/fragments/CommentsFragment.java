package com.isoftston.issuser.fusioncharge.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapter.CommentsAdapter;
import com.isoftston.issuser.fusioncharge.model.beans.CommentsBean;
import com.isoftston.issuser.fusioncharge.weights.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class CommentsFragment extends BaseFragment {
    public static final String TAG = CommentsFragment.class.getSimpleName();
    private Context context = getActivity();

    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    @Bind(R.id.comments_lv)
    ListView commentsLv;

    private String point[] = {"设施完整", "交通便利", "充电快速", "环境不错"};

    private List<CommentsBean> list;
    private CommentsAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comments;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setData();
    }

    private void setData() {
        setFlowLayoutData();
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentsBean bean = new CommentsBean();
            bean.setName("张三" + i);
            bean.setTime("4-" + i + 1);
            bean.setContent("充电费挺便宜的" + i);
            list.add(bean);
        }
        adapter = new CommentsAdapter(getActivity(), list);
        commentsLv.setAdapter(adapter);
    }

    private void setFlowLayoutData() {
        for (int i = 0; i < point.length; i++) {
            TextView tv = (TextView) getLayoutInflater().inflate(
                    R.layout.comments_point, flowLayout, false);
            tv.setText(point[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "---TEXT:" + str);
                }
            });
            flowLayout.addView(tv);//添加到父View
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
