package com.jerryzhu.androidexplore.ui.main.activity;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.LoginBridge;
import com.jerryzhu.androidexplore.presenter.main.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginBridge.View {


    @Override
    protected void initDataAndEvent() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void showLoginSuccess() {

    }
}
