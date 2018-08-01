package com.example.user.testkotlin.RetrfitDemo.httputil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class intercepter {


    private  void setitercepter(){



        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();
    }

    class LoggingInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws IOException {


            Request request=chain.request();

            return null;
        }




    }




}
