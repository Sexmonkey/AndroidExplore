package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.MainBridge;
import com.jerryzhu.androidexplore.component.RxBus;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.event.LoginEvent;
import com.jerryzhu.androidexplore.utils.CommonAlertDialog;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class MainPresenter extends BasePresenter<MainBridge.View> implements MainBridge.Presenter {

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainBridge.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent loginEvent) throws Exception {
                if(loginEvent.isLogined()){
                    mView.showLoginView();
                }else{
                    mView.showLogoutSuccess();
                }
            }
        }));
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
                        CommonAlertDialog.newInstance().cancelDialog(true);
                        RxBus.getDefault().send(new LoginEvent(false));
                    }
                }));

    }
}
