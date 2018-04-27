package com.isoftston.issuser.fusioncharge.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.isoftston.issuser.fusioncharge.MainActivity;
import com.isoftston.issuser.fusioncharge.adapter.CommentSortAdapter;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.beans.CommentSortBean;
import com.isoftston.issuser.fusioncharge.model.beans.CommentsBean;
import com.isoftston.issuser.fusioncharge.model.beans.PayInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.PublishCommentsBean;
import com.isoftston.issuser.fusioncharge.presenter.CommentPresenter;
import com.isoftston.issuser.fusioncharge.views.interfaces.CommentView;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.weights.NavBar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/25.
 */

public class PublishCommentActivity extends BaseActivity<CommentView,CommentPresenter> implements CommentView {
    @Bind(R.id.charge_location_name)
    TextView locationName;
    @Bind(R.id.charge_location_detail)
    TextView locationDetail;
    @Bind(R.id.charge_time_start)
    TextView startTime;
    @Bind(R.id.charge_time_end)
    TextView endTime;
    @Bind(R.id.charge_info_kw)
    TextView power;
    @Bind(R.id.charge_info_money)
    TextView money;
    @Bind(R.id.charge_info_tips)
    TextView tips;
    @Bind(R.id.charge_info_total)
    TextView total;
    @Bind(R.id.comment_detail_edit)
    EditText edit;
    @Bind(R.id.comment_detail_show)
    TextView show;
    @Bind(R.id.favor_lever)
    RatingBar favor;
    @Bind(R.id.publish_comment)
    Button publish;

    @Bind(R.id.flow_layout)
    GridView flowGrid;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.iv_back)
    ImageView iv_back;

    private PublishCommentsBean bean = new PublishCommentsBean();
    private Context mContext = PublishCommentActivity.this;
    private int sort = 0;
    private List<CommentSortBean> list= new ArrayList<>();
    private CommentSortAdapter adapter;
    private long pileId =1;

    @Override
    public void commentPublished() {
        show.setText(edit.getText());
        edit.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);
        publish.setVisibility(View.GONE);
        sort = 0;
        favor.setClickable(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_comment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle("发布评论");
        navBar.setImageBackground(R.drawable.nan_bg);
        sort = 0;
        presenter.queryCommentSortType();
        presenter.getPayDetailInfo(orderRecordNum);
        adapter = new CommentSortAdapter(mContext,list,true);
        flowGrid.setAdapter(adapter);
        flowGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                makeContentSort(view,position);
            }
        });
        //init the TextView content
    }

    private static String orderRecordNum ="1524387088804002";
    public static Intent getLauncher(Context context, String orderRecord) {
        Intent intent = new Intent(context, PublishCommentActivity.class);
        orderRecordNum = orderRecord;
        return intent;
    }

    @Override
    protected CommentPresenter createPresenter() {
        return new CommentPresenter();
    }

    @OnClick(R.id.publish_comment)
    public void publish() {
        //get data
        bean.setEvaluateScore(favor.getRating());
        bean.setEvaluateContent(edit.getText().toString());
        bean.setEvaluateTypeId(getSortType());
        bean.setOrderRecordNum(orderRecordNum);
        bean.setPileId(pileId);
        bean.setUserId(UserHelper.getUserId());

        presenter.publish(bean);
    }

    @Override
    public void getCommentSortAndTimes(List<CommentSortBean> times) {
    }

    @Override
    public void queryCommentInfo(List<CommentsBean> infos) {
    }

    @Override
    public void queryCommentSortType(List<CommentSortBean> sorts) {
        list = sorts;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    public void makeContentSort(View view, int position){
        int num =(int) Math.pow(2,position);
        if ((sort & num) > 0) {
            sort -= num;
            setTextAndCornerStokeColor((TextView)view, false);
        } else {
            sort += num;
            setTextAndCornerStokeColor((TextView)view, true);
        }

    }

    private String getSortType() {
        if(list.size()==0){
            return "0";
        }
        String result = "";
        int num = (int) Math.pow(2,list.size());
        int i=1;
        while(i < num ) {
            if ((sort & i) >0 ) {
                result = result + i + ",";
            }
            i=i*2;
        }
        if ((sort & num) >0 ) {
            result = result + num;
        }
        return result;
    }
    private void setTextAndCornerStokeColor(TextView view,boolean singleClick) {
        if (singleClick) {
            view.setTextColor(getResources().getColor(R.color.blue));
            view.setBackgroundResource(R.drawable.blue_stroke_corner_fill_white);
        } else {
            view.setTextColor(getResources().getColor(R.color.text_gray));
            view.setBackgroundResource(R.drawable.grey_stroke_corner_fill_white);
        }
    }

    @Override
    @OnClick(R.id.iv_back)
    public void onBackPressed() {
        if (edit.getVisibility() != View.VISIBLE) {
            mContext.startActivity(MainActivity.getLauncher(mContext));
            finish();
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(PublishCommentActivity.this)
                .setMessage("您确定要取消评论？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            mContext.startActivity(MainActivity.getLauncher(mContext));
            finish();
        }}).create();
        dialog.show();
    }

    @Override
    public void goLogin() {
    }

    //private PayInfoBean payInfoBean = new PayInfoBean();
    @Override
    public void renderData(PayInfoBean bean) {
        //payInfoBean = bean;
        refreshView(bean);
    }

    private void refreshView(PayInfoBean bean){
        if (bean == null) {
            return;
        }
        locationName.setText(bean.parkAddress);
        locationDetail.setText(bean.address);
        startTime.setText(bean.chargeStartTime);
        endTime.setText(bean.chargeEndTime);
        power.setText(Double.toString(bean.chargePowerAmount));
        money.setText(Double.toString(bean.eneryCharge));
        tips.setText(Double.toString(bean.serviceCharge));
        total.setText(Double.toString(bean.consumeTotalMoney));
        pileId = bean.chargeId;
    }
}