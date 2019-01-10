package com.jerryzhu.androidexplore.presenter.HierarchyPager;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.HierarchyPager.HierarchyPagerBridge;
import com.jerryzhu.androidexplore.core.DataManager;

import javax.inject.Inject;

public class HierarchyPagerPresenter extends BasePresenter<HierarchyPagerBridge.View> implements HierarchyPagerBridge.Presenter{

    @Inject
    public HierarchyPagerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getKnowledgeHierarchyData(boolean isShowError) {

    }
}
