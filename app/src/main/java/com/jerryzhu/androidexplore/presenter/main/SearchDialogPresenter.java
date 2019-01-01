package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.SearchDialogBridge;
import com.jerryzhu.androidexplore.bridge.main.SearchDialogBridge.Presenter;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.dao.HistoryData;

import java.util.List;

import javax.inject.Inject;

public class SearchDialogPresenter extends BasePresenter<SearchDialogBridge.View> implements Presenter {

    @Inject
    public SearchDialogPresenter(DataManager dataManager) {

        super(dataManager);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return null;
    }

    @Override
    public void clearAllHistoryData() {

    }

    @Override
    public void addHistoryData(String data) {

    }

    @Override
    public void getTopSearchData() {

    }
}
