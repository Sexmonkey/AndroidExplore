package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.ArticleDetailBridge;
import com.jerryzhu.androidexplore.core.DataManager;

import javax.inject.Inject;

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailBridge.View> implements ArticleDetailBridge.Presenter {

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
