package com.jerryzhu.androidexplore.base.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;

/**
 * Author : jerryzhu
 * Time : 2018/12/8
 * Description : this is BaseRootActiviry
 */

public abstract class BaseRootActivity<T extends BasePresenter> extends BaseActivity<T>{

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    private ViewGroup mNormalView;
    private View mLoadingView;
    private View mErrorView;
    private LottieAnimationView mAnimationView;
    private int CURRENT_STATE = NORMAL_STATE;

    @Override
    protected void initDataAndEvent() {

        mNormalView = (ViewGroup)findViewById(R.id.normal_view);

        if(mNormalView == null){
            throw new IllegalStateException("The subclass of BaseRootActivity must contain a View's id named normal_view");
        }

        if(! (mNormalView.getParent() instanceof ViewGroup)){
            throw new IllegalStateException("normalView's parent view must be a ViewGroup");
        }

        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(this, R.layout.loading_view,parent);
        View.inflate(this, R.layout.error_view, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        mErrorView = parent.findViewById(R.id.error_group);
        TextView reloadTv = mErrorView.findViewById(R.id.error_reload_tv);
        reloadTv.setOnClickListener(v -> reload());

        mAnimationView = mLoadingView.findViewById(R.id.loading_animation);

        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onDestroy() {

        if(CURRENT_STATE == LOADING_STATE && mAnimationView.isAnimating()){
            mAnimationView.cancelAnimation();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {

        if(CURRENT_STATE == LOADING_STATE){
            return;
        }

        hideCurrentView();
        CURRENT_STATE = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        mAnimationView.setAnimation("loading_bus.json");
        mAnimationView.loop(true);
        mAnimationView.playAnimation();

    }


    @Override
    public void showError() {

        if(CURRENT_STATE == ERROR_STATE){
            return;
        }

        hideCurrentView();
        CURRENT_STATE = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showNormal() {

        if(CURRENT_STATE == NORMAL_STATE){
            return;
        }

        hideCurrentView();
        CURRENT_STATE = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);

    }

    private void hideCurrentView() {

        switch (CURRENT_STATE) {

            case NORMAL_STATE:
                if(mNormalView == null){
                    return;
                }
                mNormalView.setVisibility(View.INVISIBLE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);

                break;
            case LOADING_STATE:
                mAnimationView.cancelAnimation();
                mLoadingView.setVisibility(View.GONE);
                break;

            default:

                break;


        }

    }

}