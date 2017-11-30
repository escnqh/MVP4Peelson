package com.ntanougat.mvp4peelson.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntanougat.mvp4peelson.R;
import com.ntanougat.mvp4peelson.base.BaseFragment;
import com.ntanougat.mvp4peelson.contract.PhotoInfoContract;
import com.ntanougat.mvp4peelson.entities.PhotoInfo;
import com.ntanougat.mvp4peelson.presenter.PhotoInfoPresenter;

import java.util.ArrayList;

/**
 * Created by Peelson on 2017/11/25.
 */

public class PhotoInListFragment extends BaseFragment<PhotoInfoContract.View<PhotoInfo>,PhotoInfoPresenter> implements PhotoInfoContract.View<PhotoInfo>,PhotoInfoContract.RefreshPhotoInfoListener{

    private String param;

    public PhotoInListFragment(){}

    public static PhotoInListFragment newInstance(String param){
        PhotoInListFragment fragment=new PhotoInListFragment();
        Bundle args = new Bundle();
        args.putString("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){
            param=getArguments().getString("param");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_photoinfoinlist,container,false);
        initView(v);
        return v;
    }

    private void initView(View v) {

    }

    @Override
    public void refreshPhotoInfoList(ArrayList<PhotoInfo> photoInfoList) {

    }

    @Override
    protected PhotoInfoPresenter createPresenter() {
        return new PhotoInfoPresenter(param,this);
    }

    @Override
    public void onPhotoInfoRefresh() {

    }
}
