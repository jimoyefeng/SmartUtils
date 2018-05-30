package com.example.user.testkotlin.mvp;

import android.os.Handler;

/**
 * Created by licheng on 2018/5/19.
 */
public class RequestPresenter1 extends BasePresent<RequestMvpView1> {




    public void clickRequest() {
        mMvpView.clickShow("我是点击事件触发的");

    }


}
