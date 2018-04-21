package com.isoftston.issuser.fusioncharge.views.interfaces;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.views.AppointSuccessActivity;
import com.isoftston.issuser.fusioncharge.views.ChargeDetailsActivity;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import butterknife.Bind;

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private Context context = SearchActivity.this;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.action_search)
    TextView actionSearchTv;
    @Bind(R.id.search_history_lv)
    ListView searchHistoryLv;

    @Bind(R.id.clear_history_tv)
    TextView clearHistoryTv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        clicks();
    }

    private void clicks() {
        backIv.setOnClickListener(this);
        actionSearchTv.setOnClickListener(this);
        clearHistoryTv.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:

                break;
            case R.id.action_search:

                break;
            case R.id.clear_history_tv:

                break;
        }
    }
}
