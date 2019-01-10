package com.jerryzhu.androidexplore.bridge.HierarchyPager;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.bean.HierarchyPager.KnowledgeHierarchyData;
import java.util.List;

public interface HierarchyPagerBridge {

    interface View extends AbstractRootView{

        void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList);

    }

    interface Presenter extends AbstractRootPresenter<View>{

        void getKnowledgeHierarchyData(boolean isShowError);


    }
}
