package com.wind.yuanbin.daily.mvp.P;


import com.wind.yuanbin.daily.mvp.Contract;
import com.wind.yuanbin.daily.mvp.M.Daily_details;
import com.wind.yuanbin.daily.mvp.BasePersenter;
import com.wind.yuanbin.daily.retrofit.DailyService;
import com.wind.yuanbin.daily.utils.L;
import com.wind.yuanbin.daily.utils.RetroUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wind on 2018/2/5.
 */

public class Persenter_Details extends BasePersenter<Contract.UIDetailsView>implements Contract.IPersenter_Details {

    Call<Daily_details> call;

//    Daily_details daily_details;

    public Persenter_Details(Contract.UIDetailsView view) {
        super(view);
    }

    //    public Persenter_Details(Contract.UIDetailsView view){
//        this.view = view;
//    }
    @Override
    public void getDetails(String id) {
        init(id);
        call.enqueue(new Callback<Daily_details>() {
            @Override
            public void onResponse(Call<Daily_details> call, Response<Daily_details> response) {
                show("message:" + response.message()
                        + "\nraw().body().toString(): " + response.raw().body().toString()
                        + "\nresponse.toString(): " + response.toString()
                        + "\nraw().errorBody(): " + response.errorBody()
                        + "\nraw().headers(): " + response.headers());
//                show("\n" + response.body().getTop_stories().stream().));

//                    show("byteStream " + response.raw().body().byteStream().toString());
                Daily_details daily_details = response.body();
                if (daily_details == null){
                    //没有符合的数据；
                    showErr("没有获取正确的数据");
                }else {

                    showWeb(daily_details);
                    show("getCss" + daily_details.getCss());
                }

            }

            @Override
            public void onFailure(Call<Daily_details> call, Throwable t) {
                show("fail " + t.getMessage());
            }
        });
    }

    private void init(String id) {
        DailyService service = RetroUtils.intRetro(DailyService.class);
        call = service.getDetails(id);//"9669715"
    }

    @Override
    public void show(String msg) {
        view.show(msg);
    }

    @Override
    public void showWeb(Daily_details details) {
        view.showWeb(details);
    }


    @Override
    public void showErr(String msg) {
        view.showErr(msg);
    }

    @Override
    public void onDestroy() {
        if (call != null){
            call.cancel();
            L.o("            call.cancel();\n");
        }
    }
}
