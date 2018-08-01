package com.example.user.testkotlin.utis.mvp;

import android.net.Uri;

import com.wallan.discover.library.bean.GroupMarkBean;
import com.wallan.discover.library.bean.MapTabInfo;
import com.wallan.discover.library.bean.MarkerInfo;
import com.wallan.discover.library.bean.MomentUsersBean;
import com.wallan.discover.library.bean.PersonInfoReq;
import com.wallan.discover.library.bean.PersonInforBean;
import com.wallan.discover.library.bean.PersonMapBean;
import com.wallan.discover.library.bean.ScaleReq;
import com.wallan.discover.library.bean.req.LatlonReq;
import com.wallan.discover.library.bean.req.OpenBoxReq;
import com.wallan.discover.library.bean.req.SignInReq;
import com.wallan.discover.library.bean.resp.BoxOpenResp;
import com.wallan.discover.library.bean.resp.BoxResp;
import com.wallan.discover.library.bean.resp.SignInResp;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/4/25.
 */

public interface DiscoverDataSource {

    Observable<List<MarkerInfo>> getMarkerList(String categoryLabel, double latitude, double longitude, String searchStr);

    Observable<List<String>> getSearchList(double latitude, double longitude, String searchStr);

    Observable<MapTabInfo> getMapMoreData();

    Observable<List<MarkerInfo>> getMarkerListForScale(double latitude, double longitude, String scale, float zoom);

    Observable<List<BoxResp>> getBoxList(LatlonReq latlonReq);

    Observable<BoxOpenResp> openBox(OpenBoxReq openBoxReq);

    //获取群组地图上的群
    Observable<GroupMarkBean> getGroupMarkerList(ScaleReq scaleReq);

    //获取群组地图上的获取分类条件
    Observable<List<String>> getGroupCategoryList();

    //获取地图个人
    Observable<PersonMapBean> getPersonMarkList(ScaleReq scaleReq);

    Observable<String> upLoadFile(RequestBody requestBody, MultipartBody.Part fileUrl);

    Observable<ResponseBody> downloadFile();

    Observable<String> exploreSignIn(SignInReq signInReq);
    //获取地图个人分类条件

    Observable<List<String>> getPersonCategoryList();

    Observable<PersonInforBean> getInforList(PersonInfoReq personInfoReq);

    //点赞
    Observable<String> submissionPraise(String module, String itemId, String msgId, String thumbedAcctId, String thumbState);
    Observable<String> noLookUserMoments(String momentsId);
}
