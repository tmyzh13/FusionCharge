package com.isoftston.issuser.fusioncharge.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.model.UserHelper;
import com.isoftston.issuser.fusioncharge.model.apis.CommentApi;
import com.isoftston.issuser.fusioncharge.model.apis.LoginApi;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.CommentSortBean;
import com.isoftston.issuser.fusioncharge.model.beans.CommentsBean;
import com.isoftston.issuser.fusioncharge.model.beans.LoginRequestBean;
import com.isoftston.issuser.fusioncharge.model.beans.NullPostBean;
import com.isoftston.issuser.fusioncharge.model.beans.PayInfoBean;
import com.isoftston.issuser.fusioncharge.model.beans.PublishCommentsBean;
import com.isoftston.issuser.fusioncharge.model.beans.RequestPayDetailBean;
import com.isoftston.issuser.fusioncharge.model.beans.StationBean;
import com.isoftston.issuser.fusioncharge.utils.SharePrefsUtils;
import com.isoftston.issuser.fusioncharge.views.interfaces.CommentView;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

/**
 * Created by issuser on 2018/4/25.
 */

public class CommentPresenter extends BasePresenter<CommentView> {
    private CommentApi api;
    @Override
    public void onStart() {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api =  ApiFactory.getFactory().create(CommentApi.class);
    }

    private String token = UserHelper.getSavedUser().token;
    public void publish(PublishCommentsBean bean) {
        api.publish(token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        Log.e("publish","---success");
                        view.commentPublished();
                    }
                });
    }

    public void getCommentSortAndTimes(int id) {
        StationBean bean = new StationBean();
        bean.setStationId(id);
        api.getCommentSortAndTimes(token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<List<CommentSortBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<CommentSortBean>>>(view) {
                    @Override
                    public void success(BaseData<List<CommentSortBean>> baseData) {
                        Log.e("getCommentSortAndTimes","---success");
                        view.getCommentSortAndTimes(baseData.data);
                    }
                });
    }
    public void queryCommentInfo(int id) {
        StationBean bean = new StationBean();
        bean.setStationId(id);
        api.queryCommentInfo(token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<List<CommentsBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<CommentsBean>>>(view) {
                    @Override
                    public void success(BaseData<List<CommentsBean>> baseData) {
                        Log.e("queryCommentInfo","---success");
                        view.queryCommentInfo(baseData.data);
                    }
                });
    }
    public void queryCommentSortType() {
        api.queryCommentSortType(token, new NullPostBean())
                .compose(new ResponseTransformer<>(this.<BaseData<List<CommentSortBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<CommentSortBean>>>(view) {
                    @Override
                    public void success(BaseData<List<CommentSortBean>> baseData) {
                        Log.e("queryCommentSortType","---success");
                        view.queryCommentSortType(baseData.data);
                    }
                });
    }

    public void getPayDetailInfo(String orderNum){
        RequestPayDetailBean bean=new RequestPayDetailBean();
        bean.orderRecordNum=orderNum;
        view.showLoading();
        api.getPayDetail(token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<PayInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<PayInfoBean>>(view) {
                    @Override
                    public void success(BaseData<PayInfoBean> baseData) {
                        view.renderData(baseData.data);
                    }
                });
    }
}

