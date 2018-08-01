package com.example.user.testkotlin.utis.mvp;

import com.wallan.common.utils.library.SchedulerProvider;
import com.wallan.discover.library.api.DiscoverRepository;
import com.wallan.discover.library.bean.GroupMarkBean;
import com.wallan.discover.library.bean.PersonMapBean;
import com.wallan.discover.library.bean.ScaleReq;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * created by licheng  on 2018/7/20
 */
public class MapComplexPresenter implements MapComplexContract.Present {

    private final DiscoverRepository mDiscoverRepository;
    private MapComplexContract.View mView;

    public MapComplexPresenter(MapComplexContract.View view) {
        this.mView = view;
        mDiscoverRepository = DiscoverRepository.getInstance(view.getBaseApplication().getApplicationContext());
        view.setPresenter(this);
    }

    @Override
    public void getMarkerList(ScaleReq scaleReq) {
        mDiscoverRepository.getGroupMarkerList(scaleReq)
                .subscribeOn(SchedulerProvider.ioThread())
                .observeOn(SchedulerProvider.uiThread())
                .subscribe(new Consumer<GroupMarkBean>() {
                               @Override
                               public void accept(GroupMarkBean groupMarkBean) throws Exception {
                                   mView.getMarkerListSussess(groupMarkBean);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   mView.getMarkerListFail(throwable.getMessage());
                               }
                           }
                );
    }

    @Override
    public void getCategoryList() {
        mDiscoverRepository.getGroupCategoryList()
                .subscribeOn(SchedulerProvider.ioThread())
                .observeOn(SchedulerProvider.uiThread())
                .subscribe(new Consumer<List<String>>() {
                               @Override
                               public void accept(List<String> markerInfoList) throws Exception {
                                   mView.getCategoryListSussess(markerInfoList);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   mView.getMarkerListFail(throwable.getMessage());
                               }
                           }
                );
    }

    @Override
    public void getPersonalData(ScaleReq scaleReq) {

        mDiscoverRepository.getPersonMarkList(scaleReq)
                .subscribeOn(SchedulerProvider.ioThread())
                .observeOn(SchedulerProvider.uiThread())
                .subscribe(new Consumer<PersonMapBean>() {
                               @Override
                               public void accept(PersonMapBean personMapBean) throws Exception {
                                   mView.getPersonalDataSussess(personMapBean);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   mView.getPersonalDataFail(throwable.getMessage());
                               }
                           }
                );
    }

    @Override
    public void getPersonalCategory() {
        mDiscoverRepository.getPersonCategoryList()
                .subscribeOn(SchedulerProvider.ioThread())
                .observeOn(SchedulerProvider.uiThread())
                .subscribe(new Consumer<List<String>>() {
                               @Override
                               public void accept(List<String> list) throws Exception {
                                   mView.getPersonalCategorySuccess(list);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {


                               }
                           }
                );
    }



}
