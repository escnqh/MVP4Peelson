package com.ntanougat.mvp4peelson.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;

import com.ntanougat.mvp4peelson.R;
import com.ntanougat.mvp4peelson.base.BaseActivity;
import com.ntanougat.mvp4peelson.base.BasePresenter;
import com.ntanougat.mvp4peelson.ui.fragment.PhotoInListFragment;

public class MainActivity extends BaseActivity {

    private PhotoInListFragment photoInListFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getFragmentManager();
        initView();
        initFragment();

    }

    private void initFragment() {

    }

    private void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
