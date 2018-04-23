package com.isoftston.issuser.fusioncharge.views.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.layout.PtrLollipopLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapters.HomeListAdpter;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.beans.ElectronicPileBean;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.model.beans.MyLocationBean;
import com.isoftston.issuser.fusioncharge.presenter.HomeListPresenter;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.interfaces.HomeListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/19.
 */

public class HomeListFragment extends BaseFragment<HomeListView,HomeListPresenter> implements HomeListView, PtrLollipopLayout.RefreshCallback, PtrAutoLoadMoreLayout.RefreshLoadCallback {

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
//        List<ElectronicPileBean> list =new ArrayList<>();
//        for(int i=0;i<10;i++){
//            list.add(new ElectronicPileBean());
//        }
//        adapter.replaceAll(list);
        lv_piles.setAdapter(adapter);
        lv_piles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });
        ptrLayout.disableLoading();
        presenter.setOtherLoading(false);
        presenter.getDatas();
        ptrLayout.setRefreshLoadCallback(this);
    }

    @Override
    protected HomeListPresenter createPresenter() {
        return new HomeListPresenter();
    }

    @Override
    public void rendData(List<MapDataBean> list) {
        ptrLayout.complete();

        adapter.replaceAll(list);
        MyLocationBean bean= PreferencesHelper.getData(MyLocationBean.class);
//        Log.e("yzh",bean.latitude+"---"+bean.longtitude);
//        Log.e("yzh","-0---"+ Tools.GetDistance(list.get(0).longitude,list.get(0).latitude,bean.latitude,bean.longtitude));
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        presenter.setOtherLoading(true);
        presenter.getDatas();

    }

    @Override
    public void onLoading(PtrFrameLayout frame) {

    }
}
