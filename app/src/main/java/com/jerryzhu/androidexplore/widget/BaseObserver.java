package com.jerryzhu.androidexplore.widget;

import android.text.TextUtils;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.view.AbstractRootView;
import com.jerryzhu.androidexplore.core.http.exception.ServerException;
import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private AbstractRootView mView;
    private String mErrorMsg;
    private boolean mIsShowError = true;

    public BaseObserver(AbstractRootView abstractRootView) {
        mView = abstractRootView;
    }

    public BaseObserver(AbstractRootView abstractRootView,String errorMsg) {
        mView = abstractRootView;
        mErrorMsg = errorMsg;
    }

    public BaseObserver(AbstractRootView abstractRootView,String errorMsg,boolean isShowError) {
        mView = abstractRootView;
        mErrorMsg = errorMsg;
        mIsShowError = isShowError;
    }

    @Override
    public void onError(Throwable e) {
        if(mView == null){
            return;
        }
        if(mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)){
            mView.showErrorMessage(mErrorMsg);
        }else if(e instanceof ServerException){
            mView.showErrorMessage(e.toString());
        }else if(e instanceof HttpException){
            mView.showErrorMessage(AndroidExploreApp.getInstance().getString(R.string.http_error));
        }else{
            mView.showErrorMessage(AndroidExploreApp.getInstance().getString(R.string.unKnown_error));
        }
        if(mIsShowError){
            mView.showError();
        }
    }

    @Override
    public void onComplete() {
        
    }
}
