package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.SearchListBridge;
import com.jerryzhu.androidexplore.component.RxBus;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.event.CollectEvent;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;
import javax.inject.Inject;


public class SearchListPresenter extends BasePresenter<SearchListBridge.View> implements SearchListBridge.Presenter {

    @Inject
    public SearchListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(SearchListBridge.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class).subscribe(collectEvent -> {
            if(collectEvent.isCancelCollect()){
                mView.showCancelCollectSuccess();
            }else{
                mView.showCollectSuccess();
            }
        }));
    }

    @Override
    public void getSearchList(int page, String k, boolean isShowError) {

        addSubscribe(mDataManager.getSearchList(page,k)
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        AndroidExploreApp.getInstance().getString(R.string.failed_to_obtain_search_data_list), isShowError) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showSearchList(feedArticleListData);
                    }
                }));

    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {

        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        AndroidExploreApp.getInstance().getString(R.string.collect_fail),false) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(true);
                        mView.showCollectArticleData(position,feedArticleData,feedArticleListData);
                    }
                }));

    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {

        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        AndroidExploreApp.getInstance().getString(R.string.cancel_collect_fail),false) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {

                        feedArticleData.setCollect(false);
                        mView.showCancelCollectArticleData(position,feedArticleData,feedArticleListData);
                    }

                }));

    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.getNightModeState();
    }
}
