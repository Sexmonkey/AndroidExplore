package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;

public interface UsageDialogBridge {

   interface View extends AbstractRootView{
       void showUsefulSites();

   }

   interface Presenter extends AbstractRootPresenter<View> {
       void getUserfulSites();

   }

}
