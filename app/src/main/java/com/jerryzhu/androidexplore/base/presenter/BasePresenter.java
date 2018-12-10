package com.jerryzhu.androidexplore.base.presenter;


import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.DataManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author : jerryzhu
 * <p>
 * Time : 2018/12/8
 * <p>
 * Description : this is BasePresenter
 */

public class BasePresenter<T extends AbstractRootView> implements AbstractRootPresenter<T> {

    protected T mView;
    private DataManager mDataManager;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }

    }

    @Override
    public void addRxBindingSubscribe(Disposable disposable) {

        addSubscribe(disposable);

    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.getNightModeState();
    }

    @Override
    public void setLoginStatus(int status) {

        mDataManager.setLoginStatus(status);

    }

    @Override
    public int getLoginStatus() {
        return mDataManager.getLoginStatus();
    }

    @Override
    public String getLoginAccount() {
        return mDataManager.getLoginAccount();
    }

    @Override
    public void setLoginAccount(String account) {

        mDataManager.setLoginAccount(account);

    }

    @Override
    public void setLoginPassword(String password) {

        mDataManager.setLoginPassword(password);

    }

    @Override
    public int getCurrentPage() {
        return mDataManager.getCurrentPage();
    }

    protected void addSubscribe(Disposable disposable){
        if (mCompositeDisposable == null) {

             mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(disposable);

    }
}