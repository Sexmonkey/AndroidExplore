package com.jerryzhu.androidexplore.ui.main.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.base.fragment.BaseDialogFragment;
import com.jerryzhu.androidexplore.bridge.main.UsageDialogBridge;
import com.jerryzhu.androidexplore.core.bean.mainpager.useful.UsefulData;
import com.jerryzhu.androidexplore.presenter.main.UsageDialogPresenter;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.JudgeUtils;
import com.jerryzhu.androidexplore.widget.CircularRevealAnimation;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UsageDialogFragment extends BaseDialogFragment<UsageDialogPresenter> implements UsageDialogBridge.View, CircularRevealAnimation.AnimationListener, ViewTreeObserver.OnPreDrawListener {


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
    private CircularRevealAnimation mRevealAnimation;
    private List<UsefulData> mUsefulDataList;

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

        initCircleAnimation();
        initToolBar();
        mUsefulDataList = new ArrayList<>();
        mPresenter.getUserfulSites();


    }

    private void initToolBar() {
        commonToolbarTitleTv.setText(R.string.useful_sites);

        if (mPresenter.getNightModeState()) {
            setToolbarView(R.color.comment_text, R.color.colorCard, R.drawable.ic_arrow_back_white_24dp);
        } else {
            setToolbarView(R.color.title_black, R.color.white, R.drawable.ic_arrow_back_grey_24dp);
        }

        commonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRevealAnimation.hide(commonToolbarTitleTv,mView);
            }
        });

    }

    private void setToolbarView(@ColorRes int textColor, @ColorRes int backgroundColor, @DrawableRes int navigationIcon) {
        commonToolbarTitleTv.setTextColor(ContextCompat.getColor(getContext(), textColor));
        commonToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), backgroundColor));
        commonToolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), navigationIcon));
    }


    private void initCircleAnimation() {

        mRevealAnimation = new CircularRevealAnimation();
        mRevealAnimation.setAnimationListener(this);
        commonToolbarTitleTv.getViewTreeObserver().addOnPreDrawListener(this);
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
    public void showUsefulSites(List<UsefulData> usefulDataList) {
        mUsefulDataList = usefulDataList;

        usefulTagFlowLayout.setAdapter(new TagAdapter<UsefulData>(mUsefulDataList) {

            @Override
            public View getView(FlowLayout parent, int position, UsefulData usefulData) {
                TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tag_text_view, parent, false);
                textView.setText(usefulData.getName());
                setItemBackground(textView);
                usefulTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {

                        startUsefulActivity(view,position);

                        return true;
                    }
                });
                return textView;
            }
        });


    }

    private void startUsefulActivity(View view, int position) {

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view,getString( R.string.share_view));

        UsefulData usefulData = mUsefulDataList.get(position);
        JudgeUtils.startArticleDetailActivity(getActivity(),activityOptions,
                usefulData.getId(),
                usefulData.getName(),
                usefulData.getLink(),
                false,false,true);

    }

    private void setItemBackground(TextView textView) {
        textView.setBackgroundColor(CommonUtils.randomTagColor());
        textView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
    }


    @Override
    public void onShowAnimationEnd() {
        showToast("onShowAnimationEnd");
    }

    @Override
    public void onHideAnimationEnd() {

        dismissAllowingStateLoss();
        showToast("onHideAnimationEnd");

    }

    @Override
    public boolean onPreDraw() {

        commonToolbarTitleTv.getViewTreeObserver().removeOnPreDrawListener(this);
        mRevealAnimation.show(commonToolbarTitleTv,mView);

        return true;
    }
}
