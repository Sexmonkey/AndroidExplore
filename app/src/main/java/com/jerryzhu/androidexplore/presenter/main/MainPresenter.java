package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.MainBridge;
import com.jerryzhu.androidexplore.bridge.main.MainBridge.Presenter;
import com.jerryzhu.androidexplore.core.DataManager;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainBridge.View> implements MainBridge.Presenter {

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void setCurrentPage(int page) {
        mDataManager.setCurrentPage(page);

    }

    @Override
    public void setNightModeState(boolean nightModeState) {
        mDataManager.setNightModeState(nightModeState);

    }

    @Override
    public void Logout() {

    }
}
