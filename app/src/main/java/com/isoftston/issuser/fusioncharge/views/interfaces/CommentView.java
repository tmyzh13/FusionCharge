package com.isoftston.issuser.fusioncharge.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.CommentSortBean;
import com.isoftston.issuser.fusioncharge.model.beans.CommentsBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/25.
 */

public interface CommentView extends BaseView {
    void commentPublished();
    void getCommentSortAndTimes(List<CommentSortBean> times);
    void queryCommentInfo(List<CommentsBean> infos);
    void queryCommentSortType(List<CommentSortBean> sorts);
}
