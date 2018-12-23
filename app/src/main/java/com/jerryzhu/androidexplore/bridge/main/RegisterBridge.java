package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;

public interface RegisterBridge {

    interface View extends AbstractRootView{

        void showRegisterSuccess();

    }

    interface Presenter extends AbstractRootPresenter<View>{


        void getRegisterData(String username,String password,String confirmPassword);

    }
}
