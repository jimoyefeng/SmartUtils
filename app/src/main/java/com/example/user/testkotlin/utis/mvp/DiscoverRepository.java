package com.example.user.testkotlin.utis.mvp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.wallan.common.net.library.HttpJSONResult;
import com.wallan.common.net.library.HttpManager;
import com.wallan.common.net.library.HttpResultFunc;
import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.discover.library.bean.GroupMarkBean;
import com.wallan.discover.library.bean.MapTabInfo;
import com.wallan.discover.library.bean.MarkerBody;
import com.wallan.discover.library.bean.MarkerInfo;
import com.wallan.discover.library.bean.MomentUsersBean;
import com.wallan.discover.library.bean.PersonInfoReq;
import com.wallan.discover.library.bean.PersonInforBean;
import com.wallan.discover.library.bean.PersonMapBean;
import com.wallan.discover.library.bean.ScaleReq;
import com.wallan.discover.library.bean.SearchBody;
import com.wallan.discover.library.bean.ThumbBean;
import com.wallan.discover.library.bean.req.LatlonReq;
import com.wallan.discover.library.bean.req.OpenBoxReq;
import com.wallan.discover.library.bean.req.SignInReq;
import com.wallan.discover.library.bean.resp.BoxOpenResp;
import com.wallan.discover.library.bean.resp.BoxResp;
import com.wallan.discover.library.bean.resp.SignInResp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by Administrator on 2018/4/24.
 */

public class DiscoverRepository implements DiscoverDataSource {

    private static DiscoverRepository instance;
    private Context mContext;
    private DiscoverApi mDiscoverApi;
    private GradeRestfulApi gradeRestfulApi;
    private MomentApi momentApi;
    private MomentApi commomentApi;
    private FileUploadApi mFileUploadApi;

    private DiscoverRepository(Context context) {
        this.mContext = context;
        mDiscoverApi = HttpManager.getRetrofit(HttpManager.Retrofits.cont).create(DiscoverApi.class);
        gradeRestfulApi = HttpManager.getRetrofit(HttpManager.Retrofits.malls).create(GradeRestfulApi.class);
        momentApi = HttpManager.getRetrofit(HttpManager.Retrofits.diary).create(MomentApi.class);
        commomentApi = HttpManager.getRetrofit(HttpManager.Retrofits.comment).create(MomentApi.class);
        mFileUploadApi = HttpManager.getRetrofit(HttpManager.Retrofits.trace).create(FileUploadApi.class);
    }

    public static synchronized DiscoverRepository getInstance(Context context) {
        context = EmptyUtils.checkNotNull(context).getApplicationContext();
        if (instance == null) {
            instance = new DiscoverRepository(context);
        }
        return instance;
    }

    @Override
    public Observable<List<MarkerInfo>> getMarkerList(String categoryLabel, double latitude, double longitude, String searchStr) {
        return mDiscoverApi.getMarkerList(new MarkerBody(categoryLabel, latitude, longitude, searchStr)).map(new HttpResultFunc(mContext, Collections.emptyList()));
    }

    @Override
    public Observable<List<String>> getSearchList(double latitude, double longitude, String searchStr) {
        return mDiscoverApi.getSearchList(new SearchBody(latitude, longitude, searchStr)).map(new HttpResultFunc(mContext, Collections.emptyList()));
    }

    @Override
    public Observable<MapTabInfo> getMapMoreData() {
        return mDiscoverApi.getMapMoreData().map(new HttpResultFunc<MapTabInfo>(mContext, new MapTabInfo()));
    }

    @Override
    public Observable<List<MarkerInfo>> getMarkerListForScale(double latitude, double longitude, String scale, float zoom) {
        return mDiscoverApi.getMarkerListForScale(new ScaleReq(scale, latitude, longitude, zoom)).map(new HttpResultFunc<List<MarkerInfo>>(mContext, Collections.<MarkerInfo>emptyList()));
    }

    @Override
    public Observable<List<BoxResp>> getBoxList(LatlonReq latlonReq) {
        return gradeRestfulApi.getBoxList(latlonReq).map(new HttpResultFunc<List<BoxResp>>(mContext, new ArrayList<BoxResp>()));
    }

    @Override
    public Observable<BoxOpenResp> openBox(OpenBoxReq openBoxReq) {
        return gradeRestfulApi.openBox(openBoxReq).map(new HttpResultFunc<BoxOpenResp>(mContext, new BoxOpenResp()));
    }

    @Override
    public Observable<GroupMarkBean> getGroupMarkerList(ScaleReq scaleReq) {

        return mDiscoverApi.getGroupMarkerList(scaleReq).map(new HttpResultFunc<GroupMarkBean>(mContext, new GroupMarkBean()));
    }


    @Override
    public Observable<List<String>> getGroupCategoryList() {
        return mDiscoverApi.getGroupCategoryList().map(new HttpResultFunc<List<String>>(mContext, Collections.<String>emptyList()));
    }

    @Override
    public Observable<PersonMapBean> getPersonMarkList(ScaleReq scaleReq) {

        return momentApi.getPersonmapList(scaleReq).map(new HttpResultFunc<PersonMapBean>(mContext, new PersonMapBean()));
    }

    @Override
    public Observable<String> upLoadFile(RequestBody des, MultipartBody.Part file) {
        return mFileUploadApi.uploadFile(des, file).map(new HttpResultFunc<>(mContext, ""));
    }

    @Override
    public Observable<ResponseBody> downloadFile() {
        return mFileUploadApi.downloadFile();
    }

    @Override
    public Observable<String> exploreSignIn(SignInReq signInReq) {
        return momentApi.exploreSignIn(signInReq).map(new HttpResultFunc<String>(mContext, ""));
    }

    @Override
    public Observable<List<String>> getPersonCategoryList() {
        return momentApi.getPersonCategoryList().map(new HttpResultFunc<List<String>>(mContext, Collections.<String>emptyList()));
    }


    @Override
    public Observable<PersonInforBean> getInforList(PersonInfoReq personInfoReq) {

        return momentApi.getInforList(personInfoReq).map(new HttpResultFunc<PersonInforBean>(mContext, new PersonInforBean()));

    }

    @Override
    public Observable<String> submissionPraise(String module, String itemId, String msgId, String thumbedAcctId, String thumbState) {
        ThumbBean thumbBean = new ThumbBean(itemId, msgId, thumbedAcctId, thumbState);
        return commomentApi.submissionPraise(module, thumbBean).map(new HttpResultFunc<String>(mContext, ""));
    }


    @Override
    public Observable<String> noLookUserMoments(String momentsId) {
        return commomentApi.noLookUserMoments(momentsId).map(new HttpResultFunc<String>(mContext, ""));
    }


}
