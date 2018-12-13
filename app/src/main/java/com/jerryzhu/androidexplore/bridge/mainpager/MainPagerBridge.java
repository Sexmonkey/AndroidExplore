package com.jerryzhu.androidexplore.bridge.mainpager;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;

public interface MainPagerBridge {

    interface View extends AbstractRootView{

        void showAutoLoginSuccess();

        void showAutoLoginFail();

        void showArticalList();
    }

    interface Presenter extends AbstractRootPresenter<View>{


    }


}
