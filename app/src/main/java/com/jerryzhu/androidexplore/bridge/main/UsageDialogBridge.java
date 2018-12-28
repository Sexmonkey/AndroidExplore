package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.bean.mainpager.useful.UsefulData;

import java.util.List;

public interface UsageDialogBridge {

   interface View extends AbstractRootView{
       void showUsefulSites(List<UsefulData> usefulData);

   }

   interface Presenter extends AbstractRootPresenter<View> {
       void getUserfulSites();

   }

}
