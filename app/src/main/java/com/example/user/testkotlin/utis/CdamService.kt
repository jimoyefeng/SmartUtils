package cn.yintech.cdam.data.remote

import android.text.TextUtils
import cn.yintech.cdam.BuildConfig
import cn.yintech.cdam.data.LocalDataManager
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Created by licheng on 2018/5/5.
 */
object CdamService {

    val api: CdamApi by lazy {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(parameterInterceptor)
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .build()
        return@lazy retrofit.create(CdamApi::class.java)
    }

    val fileApi:FileApi by lazy {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .build()
        return@lazy retrofit.create(FileApi::class.java)
    }

    private const val API_BASE_URL = BuildConfig.SERVICE_PLATFORM

    private val parameterInterceptor: Interceptor
        get() = Interceptor { chain ->
            val request = chain.request()
            val newUrlBuilder = request.url()
                    .newBuilder()
                    .scheme(request.url().scheme())
                    .host(request.url().host())

            val newRequest = request.newBuilder()
                    .method(request.method(), request.body())
                    .header("X-Authorization", getToken())
                    .header("X-Authorization-Refresh", getRefreshToken())
                    .url(addParameters(newUrlBuilder, LocalDataManager.getBaseRequestParams()).build())
                    .removeHeader("Pragma")
                    .build()
            chain.proceed(newRequest)
        }

    private fun addParameters(builder: HttpUrl.Builder, params: Map<String, String>): HttpUrl.Builder {
        for ((key, value) in params) {
            builder.addQueryParameter(key, value)
        }
        return builder
    }

    private fun getToken(): String {
        val currentUser = LocalDataManager.getCurrentUser()
        val token = currentUser?.userModel?.authToken?.token
        return if(TextUtils.isEmpty(token)) {
            ""
        } else {
            "Bearer ".plus(token)
        }
    }
    private fun getRefreshToken(): String {
        val currentUser = LocalDataManager.getCurrentUser()
        val refreshToken = currentUser?.userModel?.authToken?.refreshToken
        return if(TextUtils.isEmpty(refreshToken)) {
            ""
        } else {
            "Bearer ".plus(refreshToken)
        }
    }

}