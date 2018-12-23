package com.jerryzhu.androidexplore.presenter.main;

import android.text.TextUtils;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.LoginBridge;
import com.jerryzhu.androidexplore.component.RxBus;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.event.LoginEvent;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginBridge.View> implements LoginBridge.Presenter{

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getLoginData(String username, String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            mView.showSnackBar(AndroidExploreApp.getInstance().getString(R.string.account_password_null_tint));
            return;
        }
        addSubscribe(mDataManager.getLoginData(username,password)
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<LoginData>(mView,AndroidExploreApp.getInstance().getString(R.string.login_fail)) {
                    @Override
                    public void onNext(LoginData loginData) {
                        setLoginAccount(username);
                        setLoginPassword(password);
                        setLoginStatus(true);
                        RxBus.getDefault().send(new LoginEvent(true));

                        mView.showLoginSuccess();

                    }

                }));

    }
}
