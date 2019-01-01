package com.jerryzhu.androidexplore.bridge.main;

import com.jerryzhu.androidexplore.base.presenter.AbstractRootPresenter;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.bean.mainpager.search.TopSearchData;
import com.jerryzhu.androidexplore.core.dao.HistoryData;

import java.util.List;

public interface SearchDialogBridge {

    interface View extends AbstractRootView{

        void showHistoryDatda(List<HistoryData> historyDataList);

        void showTopSearchData(List<TopSearchData> topSearchDataList);

        void judjeToSearchListActivity();


    }

    interface Presenter extends AbstractRootPresenter<View>{

        List<HistoryData> loadAllHistoryData();

        void clearAllHistoryData();

        void addHistoryData(String data);

        void getTopSearchData();




    }

}
