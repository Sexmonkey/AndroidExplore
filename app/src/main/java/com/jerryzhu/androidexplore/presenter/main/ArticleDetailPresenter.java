package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.ArticleDetailBridge;
import com.jerryzhu.androidexplore.core.DataManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailBridge.View> implements ArticleDetailBridge.Presenter {

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean getAutoCacheState() {
        return false;
    }

    @Override
    public boolean getNoImageState() {
        return false;
    }

    @Override
    public void addCollectArticle(int id) {

    }

    @Override
    public void cancelCollectArticle(int id) {

    }

    @Override
    public void cancleCollectPageArticle(int id) {

    }

    @Override
    public void shareEventPermissionVerify(RxPermissions rxPermissions) {

    }
}
