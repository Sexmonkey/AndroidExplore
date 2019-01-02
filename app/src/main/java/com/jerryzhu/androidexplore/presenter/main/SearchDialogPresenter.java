package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.SearchDialogBridge;
import com.jerryzhu.androidexplore.bridge.main.SearchDialogBridge.Presenter;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.dao.HistoryData;
import com.jerryzhu.androidexplore.utils.RxUtils;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class SearchDialogPresenter extends BasePresenter<SearchDialogBridge.View> implements Presenter {

    @Inject
    public SearchDialogPresenter(DataManager dataManager) {

        super(dataManager);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return null;
    }

    @Override
    public void clearAllHistoryData() {

    }

    @Override
    public void addHistoryData(String data) {

        addSubscribe(Observable.create(new ObservableOnSubscribe<List<HistoryData>>() {

            @Override
            public void subscribe(ObservableEmitter<List<HistoryData>> e) throws Exception {

                //交给数据库按照点击时间排序存储，返回排序后所有的搜索历史列表
                List<HistoryData> historyDataList = mDataManager.addHistoryData(data);
                e.onNext(historyDataList);

            }
        })
                .compose(RxUtils.rxObSchedulerHelper())
                .subscribe(new Consumer<List<HistoryData>>() {
            @Override
            public void accept(List<HistoryData> historyDataList) throws Exception {
                mView.judjeToSearchListActivity();
            }
        }));

    }

    @Override
    public void getTopSearchData() {

    }
}
