package com.example.user.testkotlin.utis.mvp;

import com.wallan.common.net.library.HttpJSONResult;
import com.wallan.discover.library.bean.GroupMarkBean;
import com.wallan.discover.library.bean.MapTabInfo;
import com.wallan.discover.library.bean.MarkerBody;
import com.wallan.discover.library.bean.MarkerInfo;
import com.wallan.discover.library.bean.ScaleReq;
import com.wallan.discover.library.bean.SearchBody;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * DiscoverApi class
 * 地图模块api定义的接口类
 *
 * @author 刘振群
 * @date 2018/5/23
 */

public interface DiscoverApi {


    /**
     * 地图发现群数据列表api
     *
     * @param markerBody
     * @return
     */
    @POST("discovery/group/lonlat")
    Observable<HttpJSONResult<List<MarkerInfo>>> getMarkerList(@Body MarkerBody markerBody);

    /**
     * 地图搜索群拿到数据关键字列表api
     *
     * @param searchBody
     * @return
     */
    @POST("discovery/group/lonlat/recommand")
    Observable<HttpJSONResult<List<String>>> getSearchList(@Body SearchBody searchBody);


    /**
     * 地图更多模块获取数据api
     *
     * @return
     */
    @GET("discovery/group/profession")
    Observable<HttpJSONResult<MapTabInfo>> getMapMoreData();


    /**
     * 地图发现群数据列表api(通过比例尺)
     *
     * @param scaleReq
     * @return
     */
    @POST("discovery/group/lonlat/scale")
    Observable<HttpJSONResult<List<MarkerInfo>>> getMarkerListForScale(@Body ScaleReq scaleReq);


    //获取群组地图上的群

    @POST("discovery/group/lonlat/scale/v1")
    Observable<HttpJSONResult<GroupMarkBean>> getGroupMarkerList(@Body ScaleReq scaleReq);


    //获取群组地图上的获取分类条件

    @GET("discovery/group/search/category-label")
    Observable<HttpJSONResult<List<String>>> getGroupCategoryList();



}
