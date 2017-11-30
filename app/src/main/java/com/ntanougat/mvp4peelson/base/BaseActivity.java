package com.ntanougat.mvp4peelson.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Peelson on 2017/11/21.
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends Activity {
    protected P mPresenter;
    private static final String TAG = "BaseActivity";
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        //ButterKnife.bind(this);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenter();
}
