package com.wind.yuanbin.daily.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Wind on 2018/2/6.
 */

abstract public class BaseActivity<P extends BasePresenter> extends AppCompatActivity{
    protected P mPersenter;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPersenter != null){
            mPersenter.onDestroy();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPersenter = initPersenter();
    }

    public abstract P initPersenter();
}
