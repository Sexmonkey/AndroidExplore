package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;

public interface MainBridge {

    interface View extends AbstractRootView{

        /**
         * showSwitchProject
         */
        void showSwitchProject();

        void showSwitchNavigation();

        void showAutoLoginView();

        void showLogoutSuccess();

    }

    interface Presenter extends AbstractRootPresenter<View>{

        void setCurrentPage(int page);

        void setNightModeState(boolean nightModeState);

        void Logout();

    }
}
