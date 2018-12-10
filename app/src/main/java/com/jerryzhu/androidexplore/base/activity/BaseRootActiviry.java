package com.jerryzhu.androidexplore.base.activity;


import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;

/**
 * Author : jerryzhu
 * Time : 2018/12/8
 * Description : this is BaseRootActiviry
 */

public abstract class BaseRootActiviry<T extends BasePresenter> extends BaseActivity<T>{

    @Override
    protected void initDataAndEvent() {

    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }
}