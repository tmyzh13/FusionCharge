package com.isoftston.issuser.fusioncharge.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapter.SearchHistoryOrResultAdapter;
import com.isoftston.issuser.fusioncharge.model.beans.MapDataBean;
import com.isoftston.issuser.fusioncharge.presenter.HomeListPresenter;
import com.isoftston.issuser.fusioncharge.utils.alipay.CachedSearchTitleUtils;
import com.isoftston.issuser.fusioncharge.views.interfaces.HomeListView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/27.
 */

public class SearchStationTitleActivity extends BaseActivity<HomeListView,HomeListPresenter> implements HomeListView{

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.tv_search_content)
    EditText tvSearchContent;
    @Bind(R.id.iv_clear)
    ImageView ivClear;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.list_search)
    ListView listSearch;
    @Bind(R.id.tv_clear_history)
    TextView clearHistory;

    public static final String KEY_STATION = "id";
    public static final String  KEY_LONGITUDE = "longitude";
    public static final String  KEY_LATITUDE = "latitude";
    public static final String KEY_TYPE = "type";

    private SearchHistoryOrResultAdapter adapter;

    @OnClick(R.id.iv_back)
    public void goBack(){
        startActivity(MainActivity.getLauncher(SearchStationTitleActivity.this));
    }

    @OnClick(R.id.iv_clear)
    public void doClear(){
        tvSearchContent.setText("");
        tvSearchContent.setHint(R.string.please_input_key_value);
        ivSearch.setVisibility(View.VISIBLE);
        ivClear.setVisibility(View.GONE);
        adapter.resetShowHistoryData();
    }

    @OnClick(R.id.tv_search)
    public void goSearch(){
        presenter.getDatas(tvSearchContent.getText().toString());
    }
    @Override
    public void rendData(List<MapDataBean> list) {
        if (null == list || list.size() == 0) {
            return;
        }
        adapter.setResultData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goLogin() {

    }

    public static Intent getLauncher(Context context) {
        return new Intent(context,SearchStationTitleActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_station;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.resetShowHistoryData();
        tvSearchContent.setText("");
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        adapter = new SearchHistoryOrResultAdapter(SearchStationTitleActivity.this);
        listSearch.setAdapter(adapter);
        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==0){
                    return;
                }
                Bundle data = adapter.getIntent(position);
                goDetailActivity(data);
            }
        });
        tvSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ivClear.getVisibility() != View.VISIBLE) {
                    ivClear.setVisibility(View.VISIBLE);
                    ivSearch.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (null == s || s.toString().isEmpty()) {
                    ivClear.setVisibility(View.GONE);
                    ivSearch.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void goDetailActivity(Bundle bundle){
        //go detail
        Intent intent = ChargeDetailsActivity.getLauncher(SearchStationTitleActivity.this);
        intent.putExtras(bundle);
        startActivity(intent);
        if (!DOUBLE_DEFAULT.equals(bundle.getDouble(KEY_LATITUDE,DOUBLE_DEFAULT))) {
            CachedSearchTitleUtils.addHistoryData(new CachedSearchTitleUtils.CachedData(bundle.getString(KEY_STATION),bundle.getString(KEY_TYPE)
                    ,bundle.getDouble(KEY_LATITUDE),bundle.getDouble(KEY_LONGITUDE)));
        }
    }
    private static final Double DOUBLE_DEFAULT = 666.6;

    @OnClick(R.id.tv_clear_history)
    public void clearHistory(){
        CachedSearchTitleUtils.resetHistoryData();
        adapter.resetShowHistoryData();
    }

    @Override
    protected HomeListPresenter createPresenter() {
        return new HomeListPresenter();
    }
}
