package com.isoftston.issuser.fusioncharge.model.apis;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import com.isoftston.issuser.fusioncharge.constants.Urls;
import rx.Observable;
import com.isoftston.issuser.fusioncharge.model.beans.BaseData;
import com.isoftston.issuser.fusioncharge.model.beans.CommentSortBean;
import com.isoftston.issuser.fusioncharge.model.beans.CommentsBean;
import com.isoftston.issuser.fusioncharge.model.beans.NullPostBean;
import com.isoftston.issuser.fusioncharge.model.beans.PublishCommentsBean;
import com.isoftston.issuser.fusioncharge.model.beans.StationBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/25.
 */

public interface CommentApi {
    //publish comment
    @POST(Urls.COMMENT)
    Observable<BaseData> publish(@Header("AccessToken") String token,@Body PublishCommentsBean bean);

    @POST(Urls.COMMENT_SORT)
    Observable<BaseData<List<CommentSortBean>>> getCommentSortAndTimes(@Header("AccessToken") String token, @Body StationBean bean);

    @POST(Urls.COMMENT_INFO)
    Observable<BaseData<List<CommentsBean>>> queryCommentInfo(@Header("AccessToken") String token,@Body StationBean bean);

    @POST(Urls.COMMENT_INFO_TYPE)
    Observable<BaseData<List<CommentSortBean>>> queryCommentSortType(@Header("AccessToken") String token,@Body NullPostBean bean);
}
