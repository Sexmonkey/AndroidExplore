package com.jerryzhu.androidexplore.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.squareup.leakcanary.RefWatcher;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractRootDialogFragment extends DialogFragment {

    protected
    View mView;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        initEventAndData();

        return mView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null && mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
            mUnbinder = null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = AndroidExploreApp.getInstance().getRefWatcher(getActivity());
        refWatcher.watch(this);

    }

    @Override
    public void show(FragmentManager manager, String tag) {

        //防止连续点击add多个fragment
        manager.beginTransaction().remove(this).commit();

        super.show(manager, tag);
    }

    protected abstract void initEventAndData();

    protected abstract int getLayoutId();
}
