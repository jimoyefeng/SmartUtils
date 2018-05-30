package com.example.user.testkotlin.mvp;

/**
 * Created by licheng on 2018/5/19.
 */
public class BasePresent<V> {

    public V mMvpView;

    /**
     * 绑定V层
     *
     * @param view
     */
    public void attachMvpView(V view) {
        this.mMvpView = view;
    }

    /**
     * 解除绑定V层
     */
    public void detachMvpView() {
        mMvpView = null;
    }

    /**
     * 获取V层
     *
     * @return
     */
    public V getmMvpView() {
        return mMvpView;
    }
}
