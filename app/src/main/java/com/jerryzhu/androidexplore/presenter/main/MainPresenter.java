package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.MainBridge;
import com.jerryzhu.androidexplore.bridge.main.MainBridge.Presenter;
import com.jerryzhu.androidexplore.core.DataManager;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainBridge.View> implements Presenter {

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void setCurrentPage(int page) {

    }

    @Override
    public void setNightModeState(boolean nightModeState) {

    }

    @Override
    public void Logout() {

    }
}
