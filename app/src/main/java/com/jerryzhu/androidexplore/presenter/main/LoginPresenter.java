package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.LoginBridge;
import com.jerryzhu.androidexplore.core.DataManager;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginBridge.View> implements LoginBridge.Presenter{

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getLoginData(String username, String password) {

    }
}
