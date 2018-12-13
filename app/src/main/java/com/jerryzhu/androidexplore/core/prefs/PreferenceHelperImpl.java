package com.jerryzhu.androidexplore.core.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.app.Constants;

import javax.inject.Inject;

public class PreferenceHelperImpl implements PreferenceHelper {

    private final SharedPreferences mSharedPreferences;

    @Inject
    public PreferenceHelperImpl() {

        mSharedPreferences = AndroidExploreApp.getInstance().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void setLoginAccount(String account) {

        mSharedPreferences.edit().putString(Constants.ACCOUNT,account).apply();

    }

    @Override
    public void setLoginPassword(String password) {

        mSharedPreferences.edit().putString(Constants.PASSWORD,password).apply();

    }

    @Override
    public String getLoginAccount() {
        return mSharedPreferences.getString(Constants.ACCOUNT,"");
    }

    @Override
    public String getPassword() {
        return mSharedPreferences.getString(Constants.PASSWORD,"");
    }

    @Override
    public void setLoginStatus(boolean status) {

        mSharedPreferences.edit().putBoolean(Constants.LOGIN_STATUS,status).apply();

    }

    @Override
    public boolean getLoginStatus() {
        return mSharedPreferences.getBoolean(Constants.LOGIN_STATUS,false);
    }

    @Override
    public void setCookie(String domain, String cookie) {

        mSharedPreferences.edit().putString(domain,cookie).apply();

    }

    @Override
    public String getCookie(String domain) {
        return mSharedPreferences.getString(domain,"");
    }

    @Override
    public void setCurrentPage(int page) {

        mSharedPreferences.edit().putInt(Constants.CURRENT_PAGE,page).apply();

    }

    @Override
    public int getCurrentPage() {
        return mSharedPreferences.getInt(Constants.CURRENT_PAGE,0);
    }

    @Override
    public void setProjectCurrentPage(int position) {

        mSharedPreferences.edit().putInt(Constants.PROJECT_CURRENT_PAGE,position).apply();

    }

    @Override
    public int getProjectCurrentPage() {
        return mSharedPreferences.getInt(Constants.PROJECT_CURRENT_PAGE,0);
    }

    @Override
    public boolean getAutoCacheState() {
        return mSharedPreferences.getBoolean(Constants.AUTO_CACHE_STATE,false);
    }

    @Override
    public boolean getNightModeState() {
        return mSharedPreferences.getBoolean(Constants.NIGHT_MODE_STATE,false);
    }

    @Override
    public boolean getNoImagestate() {
        return mSharedPreferences.getBoolean(Constants.NO_IMAGE_STATE,false);
    }

    @Override
    public void setAutoCacheState(boolean state) {

        mSharedPreferences.edit().putBoolean(Constants.AUTO_CACHE_STATE,state).apply();

    }

    @Override
    public void setNightModeState(boolean state) {

        mSharedPreferences.edit().putBoolean(Constants.NIGHT_MODE_STATE,state).apply();

    }

    @Override
    public void setNoImageState(boolean state) {
        mSharedPreferences.edit().putBoolean(Constants.NO_IMAGE_STATE,false).apply();

    }
}
