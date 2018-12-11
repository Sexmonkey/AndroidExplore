package com.jerryzhu.androidexplore.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.squareup.leakcanary.RefWatcher;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class AbstractRootFragment extends SupportFragment {

    private Unbinder mUnbinder;
    protected boolean isInnerFragment;
    private long clickTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this,view);
        initView();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initViewAndData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
            mUnbinder = null;
        }

    }

    @Override
    public boolean onBackPressedSupport() {

        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        }else {
            if (isInnerFragment) {
                _mActivity.finish();
                return true;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if((currentTimeMillis - clickTime) > Constants.DOUBLE_INTERVAL_TIME){
                CommonUtils.showSnackMessage(_mActivity,getString(R.string.double_click_exit_tint));
                clickTime = System.currentTimeMillis();
            }

        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        RefWatcher refWatcher = AndroidExploreApp.getRefWatcher(_mActivity);
        refWatcher.watch(this);

    }


    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void initViewAndData();
}
