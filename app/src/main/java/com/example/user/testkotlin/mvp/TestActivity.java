package com.example.user.testkotlin.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.testkotlin.Moudel.WeatherBean;
import com.example.user.testkotlin.R;

/**
 * Created by licheng on 2018/5/19.
 */
public class TestActivity extends BaseMvpActivityMvp<RequestMvpView1, RequestPresenter1> implements RequestMvpView1 {

    TextView tv_click;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //创建Presenter
//        presenter = new RequestPresenter1();
//        presenter.attach(this);
    }

    @Override
    protected RequestPresenter1 createPresenter() {
        return new RequestPresenter1();
    }

    @Override
    public int getLayoyt() {
        return R.layout.activity_retrfit_test;
    }

    @Override
    public void initView() {
        tv_click = findViewById(R.id.tv_click);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clickRequest();
            }
        });
    }

    @Override
    public void initData() {

    }


    @Override
    public void requestLoading() {

    }

    @Override
    public void resultSuccess(WeatherBean result) {

    }

    @Override
    public void resultFailure(String result) {

    }

    @Override
    public void clickShow(String result) {

        Toast.makeText(TestActivity.this, "-=-=->" + result, Toast.LENGTH_SHORT).show();

    }

}
