package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.NoScrollingListView;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapters.PayStyleAdpter;
import com.isoftston.issuser.fusioncharge.model.beans.PayStyleBean;
import com.isoftston.issuser.fusioncharge.weights.CommonDialog;
import com.isoftston.issuser.fusioncharge.weights.NavBar;
import com.isoftston.issuser.fusioncharge.weights.PayFailDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/20.
 */

public class PayActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.list)
    NoScrollingListView listView;

    private PayStyleAdpter adapter;
    private Context context=PayActivity.this;
    private List<PayStyleBean> list;
    private CommonDialog commonDialog;
    private PayFailDialog dialog;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,PayActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getString(R.string.pay));
        nav.setImageBackground(R.drawable.nan_bg);

        adapter=new PayStyleAdpter(context);
        list =new ArrayList<>();
        PayStyleBean bean =new PayStyleBean();
        bean.imgRes=R.mipmap.pay_money;
        bean.name=getString(R.string.pay_balance);
        bean.hint=getString(R.string.pay_balance_hint);
        bean.type="0";
        PayStyleBean bean1=new PayStyleBean();
        bean1.imgRes=R.mipmap.list_ic_weixin;
        bean1.name=getString(R.string.pay_wechat);
        bean1.hint=getString(R.string.pay_wechat_hint);
        bean1.type="1";
        PayStyleBean bean2=new PayStyleBean();
        bean2.imgRes=R.mipmap.list_ic_zhi;
        bean2.name=getString(R.string.pay_alipay);
        bean2.hint=getString(R.string.pay_alipay_hint);
        bean2.type="2";
        list.add(bean);
        list.add(bean1);
        list.add(bean2);

        adapter.addAll(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("yzh","---"+position);
                adapter.setCurrentPosition(position);
            }
        });

        commonDialog =new CommonDialog(context,"",getString(R.string.pay_balance_not_enough),1);
        dialog=new PayFailDialog(context);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_pay)
    public void goPay(){
        //选中的支付类型
        String type=list.get(adapter.getCurrentPosition()).type;
        if(type.equals("0")){
            commonDialog.show();
            commonDialog.setDialogBackground();
        }else if(type.equals("1")){
            dialog.show();
            dialog.setTryAgainListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else if(type.equals("2")){
            startActivity(PayCompleteActivity.getLauncher(context));
        }
    }
}
