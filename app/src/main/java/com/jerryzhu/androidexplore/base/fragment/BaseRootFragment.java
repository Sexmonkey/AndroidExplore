package com.jerryzhu.androidexplore.base.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.base.presenter.BasePresenter;

public abstract class BaseRootFragment<T extends BasePresenter> extends BaseFragment<T> {


    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    private static final int ERROR_STATE = 2;
    private View mNormalView;
    private View mLoadingView;
    private View mErrorView;
    private TextView mReloadTV;
    private LottieAnimationView mLoadingAnimation;

    private int currentState = NORMAL_STATE;

    @Override
    protected void initViewAndData() {
        if(getView() == null){
            return;
        }
        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("The subclass of RootActivity must contain a View's id named normal_view");
        }
        if(!(mNormalView.getParent() instanceof ViewGroup)){

            throw new IllegalStateException("normalView must be a ViewGroup");

        }

        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(_mActivity,R.layout.loading_view,parent);
        View.inflate(_mActivity,R.layout.error_view,parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        mErrorView = parent.findViewById(R.id.error_group);
        mReloadTV = (TextView) mErrorView.findViewById(R.id.error_reload_tv);
        mReloadTV.setOnClickListener(view -> reload());
        mLoadingAnimation = (LottieAnimationView)mLoadingView.findViewById(R.id.loading_animation);
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        if (mLoadingAnimation != null && mLoadingAnimation.isAnimating()) {
            mLoadingAnimation.cancelAnimation();
        }
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE || mLoadingView == null){
            return;
        }

        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingAnimation.setAnimation("loading_bus.json");
        mLoadingAnimation.loop(true);
        mLoadingAnimation.playAnimation();

    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        if (currentState == ERROR_STATE){
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {

            case NORMAL_STATE:
                if(mNormalView == null){
                    return;
                }
                mNormalView.setVisibility(View.INVISIBLE);

                break;
            case LOADING_STATE:

                mLoadingAnimation.cancelAnimation();
                mLoadingView.setVisibility(View.GONE);

                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);

            default:

                break;


        }
    }
}
