package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;

public interface SearchListBridge {

    interface View extends AbstractRootView{

        void showSearchList(FeedArticleListData feedArticleListDatad);

        void showCollectArticleData(int position,FeedArticleData feedArticleData,FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(int position,FeedArticleData feedArticleData,FeedArticleListData feedArticleListData);


    }

    interface Presenter extends AbstractRootPresenter<View>{
        /**
         * 搜索
         * @param page page
         * @param k POST search key
         * @param isShowError If show error
         */
        void getSearchList(int page,String k,boolean isShowError);
        /**
         * Add collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void addCollectArticle(int position, FeedArticleData feedArticleData);
        /**
         * cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectArticle(int position, FeedArticleData feedArticleData);

    }
}
