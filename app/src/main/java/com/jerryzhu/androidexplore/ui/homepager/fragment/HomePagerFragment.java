package com.jerryzhu.androidexplore.ui.homepager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;

public class HomePagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_test,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static HomePagerFragment getInstance(boolean isRecreate, String params) {

        HomePagerFragment fragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1,isRecreate);
        bundle.putString(Constants.ARG_PARAM2,params);
        fragment.setArguments(bundle);

        return fragment;

    }
}
