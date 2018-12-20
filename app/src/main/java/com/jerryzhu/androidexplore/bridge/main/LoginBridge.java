package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;

public interface LoginBridge {

    interface View extends AbstractRootView{

//      显示登陆成功时的界面
        void showLoginSuccess();
    }

    interface Presenter extends AbstractRootPresenter<View>{

//      获取登陆成功后用户资料
        void getLoginData(String username ,String password);
    }
}
