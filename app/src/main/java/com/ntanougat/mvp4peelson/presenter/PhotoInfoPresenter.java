package com.ntanougat.mvp4peelson.presenter;

import com.ntanougat.mvp4peelson.base.BasePresenter;
import com.ntanougat.mvp4peelson.contract.PhotoInfoContract;
import com.ntanougat.mvp4peelson.entities.PhotoInfo;
import com.ntanougat.mvp4peelson.models.PhotoInfoModel;

/**
 * Created by Peelson on 2017/11/25.
 */

public class PhotoInfoPresenter extends BasePresenter<PhotoInfoContract.View<PhotoInfo>> implements PhotoInfoContract.Presenter{

    private PhotoInfoContract.View<PhotoInfo> mView;
    private PhotoInfoContract.Model mModel;
    private String param;

    public PhotoInfoPresenter(String param, PhotoInfoContract.View<PhotoInfo> view) {
        this.param=param;
        this.mView=view;
        this.mModel=new PhotoInfoModel();

    }


    @Override
    public void requestRefresh() {

    }
}
