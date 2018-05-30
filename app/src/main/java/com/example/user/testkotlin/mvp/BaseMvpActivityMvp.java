package com.example.user.testkotlin.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by licheng on 2018/5/19.
 */
public abstract class BaseMvpActivityMvp<V extends BaseMvpView, P extends BasePresent<V>> extends AppCompatActivity implements BaseMvpView {

    public P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }

        if (presenter == null) {
            throw new NullPointerException("presenter 不能为空!");
        }
        //绑定view
        presenter.attachMvpView((V) this);
        init();
    }


    public void init() {

        setContentView(getLayoyt());
        initView();
        initData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (presenter != null) {
            presenter.detachMvpView();
        }
    }

    /**
     * 创建Presenter
     *
     * @return 子类自己需要的Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取Presenter
     *
     * @return 返回子类创建的Presenter
     */
    public P getPresenter() {
        return presenter;
    }


    /*

    获取layout布局
     */
    public abstract int getLayoyt();


/*

初始化view
 */

    public abstract void initView();


    /*

初始化数据
 */

    public abstract void initData();


}
