package com.jerryzhu.androidexplore.presenter.main;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;
import com.jerryzhu.androidexplore.bridge.main.UsageDialogBridge;
import com.jerryzhu.androidexplore.core.DataManager;
import com.jerryzhu.androidexplore.core.bean.mainpager.useful.UsefulData;
import com.jerryzhu.androidexplore.utils.RxUtils;
import com.jerryzhu.androidexplore.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class UsageDialogPresenter extends BasePresenter<UsageDialogBridge.View> implements UsageDialogBridge.Presenter{

    @Inject
    public UsageDialogPresenter(DataManager
                                            dataManager) {
        super(dataManager);
    }

    @Override
    public void getUserfulSites() {

        addSubscribe(mDataManager.getUserfulSites()
                .compose(RxUtils.rxObSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<UsefulData>>(mView,
                        AndroidExploreApp.getInstance().getString(R.string.failed_to_obtain_useful_sites_data)) {
                    @Override
                    public void onNext(List<UsefulData> usefulData) {

                        mView.showUsefulSites(usefulData);

                    }
                }));


    }
}
