package com.jerryzhu.androidexplore.bridge.homepager;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.bean.mainpager.banner.BannerData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;

import java.util.List;

public interface HomePagerBridge {


    interface View extends AbstractRootView{

        void showAutoLoginSuccess();

        void showAutoLoginFail();

        void showArticleList(FeedArticleListData feedArticleListData,boolean isRefresh);

        void showColloectArticleData(int position, FeedArticleListData feedArticleListData, FeedArticleData feedArticleData);

        void showCancelCollectArticleData(int position,FeedArticleListData feedArticleListData,FeedArticleData feedArticleData);

        void showBannerData(List<BannerData> bannerDataList);
    }

    interface Presenter extends AbstractRootPresenter<View>{

         String getLoginPassword();

         void loadHomePagerData();

         void getFeedArticleList(boolean isShowErrorView);

         void loadMoreData();

         void addCollectArticle(int position,FeedArticleData feedArticleData);

         void cancelCollectArticle(int position,FeedArticleData feedArticleData);

         void getBannerData(boolean isShowErrorView);


         void autoRefresh(boolean isShowErrorView);

         void loadMore();

    }


}
