package com.example.user.testkotlin.RetrfitDemo.httputil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.testkotlin.R;
import com.example.user.testkotlin.RetrfitDemo.Translation;
import com.example.user.testkotlin.RetrfitDemo.interfacedemo.GetRequest_Interface;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by licheng on 2018/5/17.
 */
public class TestHTPActivity extends AppCompatActivity {

    private String TAG = "TEST";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrfit_test);
        getHttp();
    }


    public void doHttp() {
//        ApiMethods.ApiSubscribe();

    }


    public void getHttp() {

        //步骤4：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤5：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.getMov();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Translation result) {
                        // 步骤8：对返回的数据进行处理
                        result.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");
                    }
                });
    }
}


