package com.jerryzhu.androidexplore.di.module;

import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.db.DbHelper;
import com.jerryzhu.androidexplore.core.db.DbHelperImpl;
import com.jerryzhu.androidexplore.core.http.HttpHelper;
import com.jerryzhu.androidexplore.core.http.HttpHelperImpl;
import com.jerryzhu.androidexplore.core.prefs.PreferenceHelper;
import com.jerryzhu.androidexplore.core.prefs.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */

@Module
public class AppModlue {

    private final AndroidExploreApp mAndroidExploreApp;

    public AppModlue(AndroidExploreApp androidExploreApp) {
        mAndroidExploreApp = androidExploreApp;
    }

    @Provides
    @Singleton
    AndroidExploreApp provideApplicationContext(){

        return  mAndroidExploreApp;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(HttpHelperImpl httpHelper){
        return httpHelper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(DbHelperImpl dbHelper){

        return dbHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(PreferenceHelperImpl preferenceHelperImpl){
        return preferenceHelperImpl;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper,DbHelper dbHelper,PreferenceHelper preferenceHelper){

        return new DataManager(httpHelper,dbHelper,preferenceHelper);
    }


}
