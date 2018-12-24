package com.jerryzhu.androidexplore.presenter.main;

import android.text.TextUtils;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.RegisterBridge;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;
import javax.inject.Inject;

public class RegisterPresenter  extends BasePresenter<RegisterBridge.View> implements RegisterBridge.Presenter {

    @Inject
    public RegisterPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void getRegisterData(String username, String password, String confirmPassword) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword)){
            mView.showToast(AndroidExploreApp.getInstance().getString(R.string.account_password_null_tint));
            return;

        }
        if(!password.equals(confirmPassword)){
            mView.showToast((AndroidExploreApp.getInstance().getString(R.string.password_not_same)));
            return;
        }

        addSubscribe(mDataManager.getRegisterdata(username,password,confirmPassword)
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<LoginData>(mView,AndroidExploreApp.getInstance().getString(R.string.register_fail)) {
                    @Override
                    public void onNext(LoginData loginData) {
                        mView.showRegisterSuccess();
                    }
                }));

    }
}
