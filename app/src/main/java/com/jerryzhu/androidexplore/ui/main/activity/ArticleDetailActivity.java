package com.jerryzhu.androidexplore.ui.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.ArticleDetailBridge;
import com.jerryzhu.androidexplore.presenter.main.ArticleDetailPresenter;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailBridge.View {

    @Override
    protected void initDataAndEvent() {

    }

    @Override
    protected void initToolBar() {
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }
}
