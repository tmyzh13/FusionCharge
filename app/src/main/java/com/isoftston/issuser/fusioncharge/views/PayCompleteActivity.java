package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.LoadingView;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/20.
 */

public class PayCompleteActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar nav;
//    @Bind(R.id.view)
//    LoadingView view;
    private Context context=PayCompleteActivity.this;
    private String orderNum;

    public static Intent getLauncher(Context context,String orderNum){
        Intent intent =new Intent(context,PayCompleteActivity.class);
        intent.putExtra("order",orderNum);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_complete;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getString(R.string.pay_complete));
        nav.setImageBackground(R.drawable.nan_bg);

        orderNum=getIntent().getStringExtra("order");
//        view=new LoadingView(PayCompleteActivity.this);
//        view.startAnimation(0,100,3000);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_send_comment)
    public void goComment(){
        //去评价
        startActivity(PublishCommentActivity.getLauncher(PayCompleteActivity.this,orderNum));
    }

    @Override
    public void goLogin() {

    }
}
