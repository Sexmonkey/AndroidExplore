package com.jerryzhu.androidexplore.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.jerryzhu.androidexplore.BuildConfig;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.core.dao.DaoMaster;
import com.jerryzhu.androidexplore.core.dao.DaoSession;
import com.jerryzhu.androidexplore.di.component.AppComponent;
import com.jerryzhu.androidexplore.di.component.DaggerAppComponent;
import com.jerryzhu.androidexplore.di.module.AppModlue;
import com.jerryzhu.androidexplore.di.module.HttpModule;
import com.jerryzhu.androidexplore.utils.logger.TxtFormatStrategy;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AndroidExploreApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;
    private static volatile AppComponent appComponent;
    public static boolean isFirstRun = true;
    private static  AndroidExploreApp instance;

    private RefWatcher refWatcher;

    static {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary,android.R.color.white);
            return new DeliveryHeader(context);
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new BallPulseFooter(context).setAnimatingColor(ContextCompat.getColor(context,R.color.colorPrimary)));
    }

    private DaoSession mDaoSession;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {

        super.onCreate();

        initGreenDao();

        DaggerAppComponent.builder()
                .appModlue(new AppModlue(this))
                .httpModule(new HttpModule())
                .build()
                .inject(this);

        instance = this;

        initBugly();

        initLogger();

        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        refWatcher = LeakCanary.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    private void initLogger() {

        if(BuildConfig.DEBUG){
            com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag("AndroidExplore").build()));

        }
        //把log存到本地
        com.orhanobut.logger.Logger.addLogAdapter(new DiskLogAdapter(TxtFormatStrategy.newBuilder().
                tag(getString(R.string.app_name)).build(getPackageName(), getString(R.string.app_name))));

    }

    private void initBugly() {

    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        mDaoSession = daoMaster.newSession();

    }

    public static synchronized  AndroidExploreApp getInstance(){
        return instance;
    }

    public RefWatcher getRefWatcher(Context context){

        AndroidExploreApp applicationContext = (AndroidExploreApp) context.getApplicationContext();

        return applicationContext.refWatcher;

    }

    public static synchronized  AppComponent getAppComponent(){
        if (appComponent == null) {

            appComponent = DaggerAppComponent.builder()
                    .appModlue(new AppModlue(instance))
                    .build();

        }

        return appComponent;

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }
}
