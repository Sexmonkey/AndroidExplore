package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.MainBridge;
import com.jerryzhu.androidexplore.bridge.main.MainBridge.Presenter;
import com.jerryzhu.androidexplore.component.RxBus;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.BaseResponse;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.event.LoginEvent;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainBridge.View> implements MainBridge.Presenter {

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void setCurrentPage(int page) {
        mDataManager.setCurrentPage(page);

    }

    @Override
    public void setNightModeState(boolean nightModeState) {
        mDataManager.setNightModeState(nightModeState);

    }

    @Override
    public void Logout() {
        addSubscribe(mDataManager.logout()
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleLogoutResult())
                .subscribeWith(new BaseObserver<LoginData>(mView,AndroidExploreApp.getInstance().getString(R.string.logout_fail)) {
                    @Override
                    public void onNext(LoginData loginData) {
                        setLoginStatus(false);
                        setLoginAccount("");
                        setLoginPassword("");
                        mView.showLogoutSuccess();
                        RxBus.getDefault().send(new LoginEvent(false));
                    }
                }));

    }
}
