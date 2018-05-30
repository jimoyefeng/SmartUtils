package com.example.user.testkotlin.mvp;

import com.example.user.testkotlin.Moudel.WeatherBean;

/**
 * Created by licheng on 2018/5/19.
 */
public interface RequestMvpView1 extends BaseMvpView {

    //请求时展示加载
    void requestLoading();

    //请求成功
    void resultSuccess(WeatherBean result);

    //请求失败
    void resultFailure(String result);

    //点击事件显示
    void clickShow(String result);

}
