package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;

public interface ArticleDetailBridge {

    interface View extends AbstractRootView{

    }

    interface Presenter extends AbstractRootPresenter<View>{


    }
}
