package com.jerryzhu.androidexplore.core.http;

import com.jerryzhu.androidexplore.core.bean.BaseResponse;
import com.jerryzhu.androidexplore.core.bean.mainpager.banner.BannerData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.bean.mainpager.search.TopSearchData;
import com.jerryzhu.androidexplore.core.bean.mainpager.useful.UsefulData;
import com.jerryzhu.androidexplore.core.http.exception.GeekApis;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HttpHelperImpl implements HttpHelper {


    private GeekApis mGeekApis;

    @Inject
    public HttpHelperImpl(GeekApis geekApis) {
        mGeekApis = geekApis;
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {

        return mGeekApis.getBannerData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int num) {
        return mGeekApis.getFeedArticleList(num);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id) {
        return mGeekApis.cancelCollectArticle(id,-1);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id) {
        return mGeekApis.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getLoginData(String username, String password) {
        return mGeekApis.getLoginData(username,password);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id) {
        return mGeekApis.cancelCollectPageArticle(id,-1);
    }

    @Override
    public Observable<BaseResponse<LoginData>> logout() {
        return mGeekApis.logout();
    }

    @Override
    public Observable<BaseResponse<LoginData>> getRegisterdata(String username, String password, String confirmPassword) {

        return mGeekApis.getRegisterdata(username,password,confirmPassword);
    }

    @Override
    public Observable<BaseResponse<List<UsefulData>>> getUserfulSites() {
        return mGeekApis.getUsefulData();
    }

    @Override
    public Observable<BaseResponse<List<TopSearchData>>> getTopSearchData() {
        return mGeekApis.getTopSearchData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getSearchList(int page, String k) {
        return mGeekApis.getSearchList(page,k);
    }
}
