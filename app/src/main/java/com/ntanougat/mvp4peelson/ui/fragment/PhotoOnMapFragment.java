package com.ntanougat.mvp4peelson.ui.fragment;


import com.ntanougat.mvp4peelson.base.BaseFragment;
import com.ntanougat.mvp4peelson.contract.PhotoInfoContract;
import com.ntanougat.mvp4peelson.entities.PhotoInfo;
import com.ntanougat.mvp4peelson.presenter.PhotoInfoPresenter;

import java.util.ArrayList;

/**
 * Created by Peelson on 2017/11/25.
 * Place PhotoInfo on map.
 */

public class PhotoOnMapFragment extends BaseFragment<PhotoInfoContract.View<PhotoInfo>,PhotoInfoPresenter> implements PhotoInfoContract.View<PhotoInfo> {


    @Override
    public void refreshPhotoInfoList(ArrayList<PhotoInfo> photoInfoList) {

    }

    @Override
    protected PhotoInfoPresenter createPresenter() {
        return null;
    }
}
