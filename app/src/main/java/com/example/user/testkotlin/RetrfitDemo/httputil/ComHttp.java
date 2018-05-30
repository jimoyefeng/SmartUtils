package com.example.user.testkotlin.RetrfitDemo.httputil;

import com.example.user.testkotlin.RetrfitDemo.interfacedemo.GetRequest_Interface;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by licheng on 2018/5/17.
 */
public class ComHttp {

    public static volatile ComHttp comHttp;

    public ComHttp() {

    }

    public static ComHttp getComHttp() {

        if (comHttp == null) {

            synchronized (ComHttp.class) {

                if (comHttp == null) {

                    comHttp = new ComHttp();
                }

            }

        }

        return comHttp;
    }


    public void setHttp() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetRequest_Interface apiservice = retrofit.create(GetRequest_Interface.class);





    }


}
