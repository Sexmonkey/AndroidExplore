package com.jerryzhu.androidexplore.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.base.fragment.BaseDialogFragment;
import com.jerryzhu.androidexplore.bridge.main.UsageDialogBridge;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.Unbinder;

public class UsageDialogFragment extends BaseDialogFragment<UsageDialogBridge.Presenter> implements UsageDialogBridge.View {


    @BindView(R.id.common_toolbar_title_tv)
    TextView commonToolbarTitleTv;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.useful_tag_flow_layout)
    TagFlowLayout usefulTagFlowLayout;
    @BindView(R.id.usage_scroll_view)
    NestedScrollView usageScrollView;
    @BindView(R.id.fragment_usage_dialog_group)
    CoordinatorLayout fragmentUsageDialogGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

        initDialog();
    }

    @Override
    protected void initEventAndData() {

    }


    private void initDialog() {

        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int with = (int)(metrics.widthPixels * 0.98);
        window.setLayout(with,WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过度动画
        window.setWindowAnimations(R.style.DialogEmptyAnimation);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_usage_dialog;
    }

    @Override
    public void showUsefulSites() {

    }


}
