package com.jerryzhu.androidexplore.core;

import com.jerryzhu.androidexplore.core.bean.mainpager.banner.BannerData;
import com.jerryzhu.androidexplore.core.bean.BaseResponse;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.db.DbHelper;
import com.jerryzhu.androidexplore.core.http.HttpHelper;
import com.jerryzhu.androidexplore.core.prefs.PreferenceHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */
public class DataManager implements HttpHelper,DbHelper,PreferenceHelper {

    private final HttpHelper mHttpHelper;
    private final DbHelper mDbHelper;
    private final PreferenceHelper mPreferenceHelper;

    public DataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        mHttpHelper = httpHelper;
        mDbHelper = dbHelper;
        mPreferenceHelper = preferenceHelper;
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelper.setLoginAccount(account);

    }

    @Override
    public void setLoginPassword(String password) {
        mPreferenceHelper.setLoginPassword(password);

    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelper.getLoginAccount();
    }

    @Override
    public String getPassword() {
        return mPreferenceHelper.getPassword();
    }

    @Override
    public void setLoginStatus(boolean status) {

        mPreferenceHelper.setLoginStatus(status);

    }

    @Override
    public boolean getLoginStatus() {
        return mPreferenceHelper.getLoginStatus();
    }

    @Override
    public void setCookie(String domain, String cookie) {
        mPreferenceHelper.setCookie(domain,cookie);

    }

    @Override
    public String getCookie(String domain) {
        return mPreferenceHelper.getCookie(domain);
    }

    @Override
    public void setCurrentPage(int position) {

        mPreferenceHelper.setCurrentPage(position);

    }

    @Override
    public int getCurrentPage() {
        return mPreferenceHelper.getCurrentPage();
    }

    @Override
    public void setProjectCurrentPage(int position) {
        mPreferenceHelper.setProjectCurrentPage(position);

    }

    @Override
    public int getProjectCurrentPage() {
        return mPreferenceHelper.getProjectCurrentPage();
    }

    @Override
    public boolean getAutoCacheState() {
        return mPreferenceHelper.getAutoCacheState();
    }

    @Override
    public boolean getNightModeState() {
        return mPreferenceHelper.getNightModeState();
    }

    @Override
    public boolean getNoImagestate() {
        return mPreferenceHelper.getNoImagestate();
    }

    @Override
    public void setAutoCacheState(boolean state) {

        mPreferenceHelper.setAutoCacheState(state);

    }

    @Override
    public void setNightModeState(boolean state) {

        mPreferenceHelper.setNightModeState(state);


    }

    @Override
    public void setNoImageState(boolean state) {

        mPreferenceHelper.setNoImageState(state);

    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return mHttpHelper.getBannerData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int num) {
        return mHttpHelper.getFeedArticleList(num);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id) {
        return mHttpHelper.cancelCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id) {
        return mHttpHelper.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getLoginData(String userName, String loginPassword) {
        return mHttpHelper.getLoginData(userName,loginPassword);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id) {
        return mHttpHelper.cancelCollectPageArticle(id);
    }
     @Override
    public Observable<BaseResponse<LoginData>> logout() {
        return mHttpHelper.logout();
    }

    @Override
    public Observable<BaseResponse<LoginData>> getRegisterdata(String username, String password, String confirmPassword) {

        return mHttpHelper.getRegisterdata(username,password,confirmPassword);

    }
}
