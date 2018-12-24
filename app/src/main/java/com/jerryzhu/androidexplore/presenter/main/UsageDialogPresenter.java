package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.UsageDialogBridge;
import com.jerryzhu.androidexplore.core.DataManager;

public class UsageDialogPresenter extends BasePresenter<UsageDialogBridge.View> implements UsageDialogBridge.Presenter{

    public UsageDialogPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getUserfulSites() {


    }
}
