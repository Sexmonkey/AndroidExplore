package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.tbruyelle.rxpermissions2.RxPermissions;

public interface ArticleDetailBridge {


    interface View extends AbstractRootView{

        void showCollectArticleData(FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(FeedArticleListData feedArticleListData);

        void shareEvent();

        void shareError();

    }

    interface Presenter extends AbstractRootPresenter<View>{

        boolean getAutoCacheState();

        boolean getNoImageState();

        void addCollectArticle(int id);

        void cancelCollectArticle(int id);

        void cancleCollectPageArticle(int id);

//      校验分享需要的权限
        void shareEventPermissionVerify(RxPermissions rxPermissions);

    }
}
