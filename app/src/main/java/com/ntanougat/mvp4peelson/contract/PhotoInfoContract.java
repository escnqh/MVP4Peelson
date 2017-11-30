package com.ntanougat.mvp4peelson.contract;

import com.ntanougat.mvp4peelson.base.BaseContract;

import java.util.ArrayList;

/**
 * Created by Peelson on 2017/11/25.
 */

public class PhotoInfoContract extends BaseContract {

    public interface Model{

        void getPhotoInfo();
    }

    public interface View<T>{

        void refreshPhotoInfoList(ArrayList<T> photoInfoList);
    }

    public interface Presenter{

        void requestRefresh();

    }

    public interface RefreshPhotoInfoListener{
        void onPhotoInfoRefresh();
    }
}
