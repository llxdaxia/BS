package cn.lemon.bs.data;

import java.util.concurrent.TimeUnit;

import cn.lemon.bs.BuildConfig;
import cn.lemon.common.net.HeadersInterceptor;
import cn.lemon.common.net.LogInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linlongxin on 2017.5.5.
 */

public class RetrofitModel {

    private static final String BASE_URL = "http://zhou.lemon95.cn/";
    private static final String NET_LOG_TAG = "bs_net";
    private static NetService mService;
    private static Retrofit mRetrofit;

    public static NetService getNetService(){
        if(mService == null){
            synchronized (RetrofitModel.class){
                if(mService == null){
                    mService = getRetrofit().create(NetService.class);
                }
            }
        }
        return mService;
    }

    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                //debug模式下添加日志拦截器
                LogInterceptor logInterceptor = new LogInterceptor()
                        .setLogTag(NET_LOG_TAG);
                mOkHttpClientBuilder
                        .addInterceptor(logInterceptor);
            }
            mOkHttpClientBuilder.addInterceptor(new HeadersInterceptor());
            mOkHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
            mOkHttpClientBuilder.readTimeout(20, TimeUnit.SECONDS);
            mOkHttpClientBuilder.writeTimeout(20, TimeUnit.SECONDS);
            mOkHttpClientBuilder.retryOnConnectionFailure(true);
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkHttpClientBuilder.build())
                    .build();
        }
        return mRetrofit;
    }
}
