package com.jerryzhu.androidexplore.base.view;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */
public interface AbstractRootView {

    /**
     * @param useNightMode
     */
    void useNightMode(boolean useNightMode);

    /**
     * @param msg
     */
    void showErrorMessage(String msg);

    /**
     * showNormal
     */
    void showNormal();

    /**
     * showError
     */
    void showError();

    /**
     * showLoading
     */
    void showLoading();

    /**
     * reload
     */
    void reload();

    /**
     * showLoginView
     */
    void showLoginView();

    /**
     * showLogoutView
     */
    void showLogoutView();

    /**
     * showCollectSuccess
     */
    void showCollectSuccess();

    void showCancelCollectSuccess();

    void showToast(String msg);

    void showSnackBar(String msg);
}
