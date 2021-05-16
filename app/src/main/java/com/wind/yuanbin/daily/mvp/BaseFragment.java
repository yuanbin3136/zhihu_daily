package com.wind.yuanbin.daily.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by yuanb on 2018/2/24.
 */

abstract public class BaseFragment<P extends BasePresenter> extends Fragment {
    protected P mPersenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPersenter != null){
            mPersenter.onDestroy();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPersenter = initPersenter();
    }

    public abstract P initPersenter();
}
