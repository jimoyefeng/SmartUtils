package com.example.user.testkotlin.RetrfitDemo.httputil;

import android.content.Context;

import com.example.user.testkotlin.RetrfitDemo.ResultBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by licheng on 2018/5/17.
 */
public class MyObserver<T> implements Observer<ResultBean> {

    private ObserverOnNextListener listener;
    private Context context;

    public MyObserver(Context context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResultBean resultBean) {

        listener.onSuccess(resultBean.getData());

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
