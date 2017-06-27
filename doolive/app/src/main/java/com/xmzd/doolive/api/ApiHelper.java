package com.xmzd.doolive.api;

import com.xmzd.doolive.utils.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date    2017/6/26
 * Author  xiaomao
 */

public class ApiHelper {

    private final int TIMEOUT_CONNECT = 10;
    private final int TIMEOUT_READ = 10;
    private final int TIMEOUT_WRITE = 10;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private ApiService mApiService;

    private ApiHelper() {
        initOkHttp();
        initRetrofit();
    }

    public static ApiHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static ApiHelper INSTANCE = new ApiHelper();
    }

    /**
     * 初始化OkHttpClient
     */
    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // set timeout
        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS);
        // set HttpLoggingInterceptor
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d("xmzd", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(httpLoggingInterceptor);
        // retry
        builder.retryOnConnectionFailure(true);
        // client
        mOkHttpClient = builder.build();
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    public ApiService getApiService() {
        if (mApiService == null) {
            mApiService = mRetrofit.create(ApiService.class);
        }
        return mApiService;
    }
}
