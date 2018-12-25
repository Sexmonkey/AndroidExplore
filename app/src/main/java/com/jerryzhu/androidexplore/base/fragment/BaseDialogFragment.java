package com.jerryzhu.androidexplore.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseDialogFragment<T extends AbstractRootPresenter> extends AbstractRootDialogFragment implements AbstractRootView {

    T mPresenter;

    @Override
    public void onAttach(Context context) {

        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mPresenter != null){
            mPresenter = null;
        }
    }

    @Override
    public void onDestroyView() {

        if(mPresenter != null){
            mPresenter.detachView();
        }

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void useNightMode(boolean useNightMode) {

    }

    @Override
    public void showErrorMessage(String msg) {

        if (getActivity() != null){
           CommonUtils.showMessage(getActivity(),msg);
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
        if(getActivity() != null){
            CommonUtils.showMessage(getActivity(),msg);
        }

    }

    @Override
    public void showSnackBar(String msg) {
        if(getActivity() != null){
            CommonUtils.showSnackMessage(getActivity(),msg);
        }

    }
}
