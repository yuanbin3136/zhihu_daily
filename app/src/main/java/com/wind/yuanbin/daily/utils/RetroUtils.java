package com.wind.yuanbin.daily.utils;

import com.wind.yuanbin.daily.BuildConfig;
import com.wind.yuanbin.daily.mvp.Contract;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yuanb on 2018/2/18.
 */

public class RetroUtils {
    public static <T> T intRetro(Class<T> service){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            // development build
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            // production build
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Contract.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
//                .addCallAdapterFactory()
                .build();

        return retrofit.create(service);
    }
}
