package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.SplashBridge;
import com.jerryzhu.androidexplore.core.DataManager;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashPresenter extends BasePresenter<SplashBridge.View> implements SplashBridge.Presenter {

    @Inject
    public SplashPresenter(DataManager dataManager) {
        super(dataManager);

    }


}
