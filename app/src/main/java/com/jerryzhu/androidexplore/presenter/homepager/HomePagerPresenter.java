package com.jerryzhu.androidexplore.presenter.homepager;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.homepager.HomePagerBridge;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.BaseResponse;
import com.jerryzhu.androidexplore.core.bean.mainpager.banner.BannerData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class HomePagerPresenter extends BasePresenter<HomePagerBridge.View> implements HomePagerBridge.Presenter {

    private boolean mIsRefresh;
    private int mCurrentPage;

    @Inject
    public HomePagerPresenter(DataManager dataManager) {

        super(dataManager);
    }

    @Override
    public void attachView(HomePagerBridge.View view) {
        super.attachView(view);
        registerEevent();
    }

    private void registerEevent() {


    }

    @Override
    public String getLoginPassword() {
        return null;
    }

    @Override
    public void loadHomePagerData() {
        Observable<BaseResponse<List<BannerData>>> bannerDataObservable = mDataManager.getBannerData();
        Observable<BaseResponse<FeedArticleListData>> feedArticleListObservable = mDataManager.getFeedArticleList(0);
        Observable<BaseResponse<LoginData>> loginDataObservable = mDataManager.getLoginData(getLoginAccount(),getLoginPassword());
        addSubscribe(Observable.zip(loginDataObservable,bannerDataObservable,feedArticleListObservable,this::createResponseDataMap)
                .compose(RxUtils.rxObSchedulerHelper())
                .subscribeWith(new BaseObserver<HashMap<String, Object>>(mView) {
                    @Override
                    public void onNext(HashMap<String, Object> map) {
                       BaseResponse<LoginData> loginData = CommonUtils.cast(map.get(Constants.LOGIN_DATA));
                        if (loginData.getErrorCode() == BaseResponse.SUCCESS) {
                            loginSuccess(loginData.getData());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );



    }

    private void loginSuccess(LoginData loginData) {
        mDataManager.setLoginAccount(loginData.getUsername());
        mDataManager.setLoginPassword(loginData.getPassword());
        mDataManager.setLoginStatus(true);
        mView.showAutoLoginSuccess();
    }

    @NonNull
    private HashMap<String, Object> createResponseDataMap(BaseResponse<LoginData> loginData,
                              BaseResponse<List<BannerData>> bannerData,
                              BaseResponse<FeedArticleListData> feedArticleList) {

        HashMap<String, Object> map = new HashMap<>(3);

        map.put(Constants.LOGIN_DATA,loginData);
        map.put(Constants.BANNER_DATA,bannerData);
        map.put(Constants.ARTICLE_DATA,feedArticleList);

        return map;


    }

    @Override
    public void getFeedArticleList(boolean isShowErrorView) {
        addSubscribe(mDataManager.getFeedArticleList(mCurrentPage)
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(
                        mView,
                        AndroidExploreApp.getInstance().getString(R.string.failed_to_obtain_article_list),
                        isShowErrorView) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showArticleList(feedArticleListData,mIsRefresh);
                    }
                })

        );

    }

    @Override
    public void loadMoreData() {

        mIsRefresh = false;
        mCurrentPage++;

        loadMore();
    }


    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        AndroidExploreApp.getInstance().getString(R.string.collect_success)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(true);
                        mView.showColloectArticleData(position,feedArticleListData,feedArticleData);

                    }
                })
        );

    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        AndroidExploreApp.getInstance().getString(R.string.cancel_collect_fail),
                        false){
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(false);
                        mView.showCancelCollectArticleData(position,feedArticleListData,feedArticleData);
                    }

                })
        );

    }

    @Override
    public void getBannerData(boolean isShowError) {
        addSubscribe(mDataManager.getBannerData()
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<BannerData>>(
                        mView,
                        AndroidExploreApp.getInstance().getString(R.string.failed_to_obtain_banner_data),
                        isShowError) {
                    @Override
                    public void onNext(List<BannerData> bannerData) {
                        mView.showBannerData(bannerData);
                    }
                })
        );

    }

    @Override
    public void autoRefresh(boolean isShowError) {
        mIsRefresh = true;
        mCurrentPage = 0;

        getBannerData(isShowError);

        getFeedArticleList(isShowError);

    }

    @Override
    public void loadMore() {

        addSubscribe(mDataManager.getFeedArticleList(mCurrentPage)
                      .compose(RxUtils.rxObSchedulerHelper())
                      .compose(RxUtils.handleResult()).
                        subscribeWith(new BaseObserver<FeedArticleListData>(
                                mView,
                                AndroidExploreApp.getInstance().getString(R.string.failed_to_obtain_article_list),
                                false) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showArticleList(feedArticleListData,mIsRefresh);
                    }}));

    }

}
