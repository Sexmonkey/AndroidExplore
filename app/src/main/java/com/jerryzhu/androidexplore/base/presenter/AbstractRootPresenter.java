package com.jerryzhu.androidexplore.base.presenter;

import com.jerryzhu.androidexplore.base.view.AbstractRootView;

import io.reactivex.disposables.Disposable;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */
public interface AbstractRootPresenter<T extends AbstractRootView> {

    /**
     * @param view
     */
    void attachView(T view);

    /**
     * detachView
     */
    void detachView();


    /**
     * @param disposable
     */
    void addRxBindingSubscribe(Disposable disposable);

    /**
     * @return
     */
    boolean getNightModeState();

    /**
     * @param status
     */
    void setLoginStatus(int status);

    /**
     * @return
     */
    int getLoginStatus();

    /**
     * @return
     */
    String getLoginAccount();

    /**
     * @param account
     */
    void setLoginAccount(String account);

    /**
     * @param password
     */
    void setLoginPassword(String password);

    /**
     * @return
     */
    int getCurrentPage();

}
