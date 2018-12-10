package com.jerryzhu.androidexplore.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.utils.CommonUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends AbstractRootPresenter> extends AbstractRootFragment implements AbstractRootView {

    @Inject
    protected T mPresent;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresent != null) {
            mPresent.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresent != null) {
            mPresent.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresent != null) {
            mPresent = null;
        }
    }

    @Override
    public void useNightMode(boolean useNightMode) {

    }

    @Override
    public void showErrorMessage(String msg) {
        if(isAdded()){
           CommonUtils.showMessage(_mActivity,msg);
        }
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {


    }

    @Override
    public void reload() {

    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    @Override
    public void showToast(String msg) {
        CommonUtils.showMessage(_mActivity,msg);

    }

    @Override
    public void showSnackBar(String msg) {

        CommonUtils.showSnackMessage(_mActivity,msg);

    }
}
