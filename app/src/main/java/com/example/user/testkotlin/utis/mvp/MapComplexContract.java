package com.example.user.testkotlin.utis.mvp;

import com.wallan.base.app.library.BasePresenter;
import com.wallan.base.app.library.BaseView;
import com.wallan.discover.library.bean.GroupMarkBean;
import com.wallan.discover.library.bean.PersonMapBean;
import com.wallan.discover.library.bean.ScaleReq;

import java.util.List;

/**
 * created by licheng  on 2018/7/20
 */
public interface MapComplexContract {

    interface Present extends BasePresenter {
        void getMarkerList(ScaleReq scaleReq);

        void getCategoryList();

        void getPersonalData(ScaleReq scaleReq);

        void getPersonalCategory();
    }

    interface View extends BaseView<Present> {
        void getMarkerListSussess(GroupMarkBean groupMarkBean);

        void getMarkerListFail(String errMsg);

        void getCategoryListSussess(List<String> categoryList);

        void getPersonalDataSussess(PersonMapBean markerInfoList);

        void getPersonalDataFail(String errMsg);

        void getPersonalCategorySuccess(List<String> list);
    }
}
