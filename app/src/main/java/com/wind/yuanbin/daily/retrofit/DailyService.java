package com.wind.yuanbin.daily.retrofit;

import com.wind.yuanbin.daily.mvp.Contract;
import com.wind.yuanbin.daily.mvp.M.DailyModel;
import com.wind.yuanbin.daily.mvp.M.Daily_details;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Wind on 2018/2/5.
 */

public interface DailyService {
//    @GET (Contract.URL_daily_content)
    @GET (Contract.URL_daily)
    Call<DailyModel> getToday();

    @GET ("{id}")
//    @GET (Contract.URL_daily)
    Call<Daily_details> getDetails(@Path("id") String id);
}
