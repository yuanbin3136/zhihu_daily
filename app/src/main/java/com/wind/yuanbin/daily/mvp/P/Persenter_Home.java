package com.wind.yuanbin.daily.mvp.P;


import com.wind.yuanbin.daily.mvp.BasePresenter;
import com.wind.yuanbin.daily.mvp.Contract;
import com.wind.yuanbin.daily.mvp.M.DailyModel;
import com.wind.yuanbin.daily.retrofit.DailyService;
import com.wind.yuanbin.daily.utils.L;
import com.wind.yuanbin.daily.utils.RetroUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wind on 2018/2/5.
 */

public class Persenter_Home extends BasePresenter<Contract.UIView> implements Contract.IPersenter_Home {

    Call<DailyModel> call;

    DailyModel dailyModel;

    public Persenter_Home(Contract.UIView view) {
        super(view);
    }

    //    public Persenter_Home(Contract.UIView view){
//        this.view = view;
//    }
    @Override
    public void getToday() {
        init();

        call.enqueue(new Callback<DailyModel>() {
            @Override
            public void onResponse(Call<DailyModel> call, Response<DailyModel> response) {
                show("message:" + response.message()
                        + "\nraw().body().toString(): " + response.raw().body().toString()
                        + "\nresponse.toString(): " + response.toString()
                        + "\nraw().errorBody(): " + response.errorBody()
                        + "\nraw().headers(): " + response.headers());
//                show("\n" + response.body().getTop_stories().stream().));

//                    show("byteStream " + response.raw().body().byteStream().toString());
                dailyModel = response.body();
                List<DailyModel.Top_stories> top_stories = response.body().getTop_stories();
                if (top_stories == null){
                    //没有符合的数据；
                    showErr("没有获取正确的数据");
                }else {
                    top_stories.stream()
                            .forEach(top ->
                                    show(top.getId() + "image : " + top.getImage()));
                    showList(response.body());
                }

            }

            @Override
            public void onFailure(Call<DailyModel> call, Throwable t) {
                show("fail " + t.getMessage());
            }
        });
    }

    private void init() {
        DailyService service = RetroUtils.intRetro(DailyService.class);
        call = service.getToday();
    }

    @Override
    public void show(String msg) {
        view.show(msg);
    }

    @Override
    public void showList(DailyModel model) {
        view.showList(model);
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
