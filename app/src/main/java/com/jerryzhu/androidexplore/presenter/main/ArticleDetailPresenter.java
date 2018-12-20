package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.ArticleDetailBridge;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailBridge.View> implements ArticleDetailBridge.Presenter {

    @Inject
    public ArticleDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImagestate();
    }

    @Override
    public void addCollectArticle(int id) {

        addSubscribe(mDataManager.addCollectArticle(id)
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(
                        mView,AndroidExploreApp.getInstance().getString(R.string.collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showCollectArticleData(feedArticleListData);
                    }
                }));

    }

    @Override
    public void cancelCollectArticle(int id) {
        addSubscribe(mDataManager.cancelCollectArticle(id)
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(
                        mView,AndroidExploreApp.getInstance().getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showCancelCollectArticleData(feedArticleListData);
                    }
                }));

    }

    @Override
    public void cancleCollectPageArticle(int id) {

        addSubscribe(mDataManager.cancelCollectPageArticle(id)
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(
                        mView,AndroidExploreApp.getInstance().getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showCancelCollectArticleData(feedArticleListData);
                    }
                }));

    }

    @Override
    public void shareEventPermissionVerify(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted){
                           mView.shareEvent();
                        }else{
                            mView.shareError();

                        }
                    }
                }));

    }
}
