package com.wind.yuanbin.daily.mvp;

/**
 * Created by Wind on 2018/2/6.
 */

abstract public class BasePresenter<V> {
    protected V view;
    public BasePresenter(V view){
        this.view = view;
    }
    abstract public void onDestroy();
}
