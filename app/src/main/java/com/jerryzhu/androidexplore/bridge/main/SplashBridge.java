package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */
public interface SplashBridge {


    interface View extends AbstractRootView{

        void jumpTomainActivity();

    }

    interface Presenter extends AbstractRootPresenter<View>{


    }
}
