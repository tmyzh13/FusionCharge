package com.isoftston.issuser.fusioncharge.views.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapters.HomeListAdpter;
import com.isoftston.issuser.fusioncharge.model.beans.ElectronicPileBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/19.
 */

public class HomeListFragment extends BaseFragment {

    @Bind(R.id.lv_piles)
    AutoLoadMoreListView lv_piles;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    public HomeListAdpter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        adapter =new HomeListAdpter(getContext());
        List<ElectronicPileBean> list =new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new ElectronicPileBean());
        }
        adapter.replaceAll(list);
        lv_piles.setAdapter(adapter);
        lv_piles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
