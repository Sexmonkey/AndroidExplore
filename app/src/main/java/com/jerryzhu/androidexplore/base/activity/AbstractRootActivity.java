package com.jerryzhu.androidexplore.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jerryzhu.androidexplore.component.ActivityCollector;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */
public abstract  class AbstractRootActivity extends SupportActivity {

    private Unbinder mUnbinder;
    protected AbstractRootActivity mAbstractRootActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mAbstractRootActivity = this;
        ActivityCollector.getInstance().addActivity(this);
        onViewCreated();
        initToolBar();
        initDataAndEvent();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityCollector.getInstance().removeActivity(this);

        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }


    protected abstract void initDataAndEvent();

    protected abstract void initToolBar();

    protected abstract void onViewCreated();


    protected abstract int getLayoutId();
}
