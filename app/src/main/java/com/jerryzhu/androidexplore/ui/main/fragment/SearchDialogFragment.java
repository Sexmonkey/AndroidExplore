package com.jerryzhu.androidexplore.ui.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.fragment.BaseDialogFragment;
import com.jerryzhu.androidexplore.bridge.main.SearchDialogBridge;
import com.jerryzhu.androidexplore.core.bean.mainpager.search.TopSearchData;
import com.jerryzhu.androidexplore.core.dao.HistoryData;
import com.jerryzhu.androidexplore.presenter.main.SearchDialogPresenter;
import com.jerryzhu.androidexplore.ui.main.adapter.SearchHistroyAdapter;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.JudgeUtils;
import com.jerryzhu.androidexplore.utils.KeyBoardUtils;
import com.jerryzhu.androidexplore.widget.CircularRevealAnimation;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class SearchDialogFragment extends BaseDialogFragment<SearchDialogPresenter> implements SearchDialogBridge.View, CircularRevealAnimation.AnimationListener, ViewTreeObserver.OnPreDrawListener {

    private List<TopSearchData> mTopSearchDataList;

    @BindView(R.id.search_toolbar_back)
    ImageButton searchToolbarBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.search_tint_tv)
    TextView searchTintTv;
    @BindView(R.id.search_toolbar)
    Toolbar searchToolbar;
    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout topSearchFlowLayout;
    @BindView(R.id.search_history_clear_all_tv)
    TextView searchHistoryClearAllTv;
    @BindView(R.id.search_history_null_tint_tv)
    TextView searchHistoryNullTintTv;
    @BindView(R.id.search_history_rv)
    RecyclerView searchHistoryRv;
    @BindView(R.id.search_scroll_view)
    NestedScrollView searchScrollView;
    @BindView(R.id.search_floating_action_btn)
    FloatingActionButton searchFloatingActionBtn;
    @BindView(R.id.search_coordinator_group)
    CoordinatorLayout searchCoordinatorGroup;
    private CircularRevealAnimation mCircularRevealAnimation;
    private SearchHistroyAdapter mSearchHistroyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

        initDialog();
    }

    private void initDialog() {
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        int with = (int) (metrics.widthPixels * 0.98);
        Window window = getDialog().getWindow();
        window.setLayout(with, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.DialogEmptyAnimation);

    }

    @Override
    protected void initEventAndData() {
        initRevealAnimation();
        initRecyclerView();
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editSearch.getText().toString())) {
                    searchTintTv.setText(AndroidExploreApp.getInstance().getString(R.string.search_null_tint));
                } else {
                    searchTintTv.setText("");
                }

            }
        });

        mPresenter.addRxBindingSubscribe(RxView.clicks(tvSearch)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return !TextUtils.isEmpty(editSearch.getText().toString().trim());
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.addHistoryData(editSearch.getText().toString().trim());

                        setHistoryTvStatus(false);
                    }
                }));

        showHistoryDatda(mPresenter.loadAllHistoryData());
        mPresenter.getTopSearchData();

    }

    private void setHistoryTvStatus(boolean isClearAll) {

        if (isClearAll) {
            setHistoryTvStatus(View.VISIBLE, R.color.search_grey_gone, R.drawable.ic_clear_all_gone);
        } else {
            setHistoryTvStatus(View.GONE, R.color.search_grey, R.drawable.ic_clear_all);
        }

    }

    private void setHistoryTvStatus(int visibility, @ColorRes int textColor, @DrawableRes int clearDrawable) {
        Drawable drawable;
        searchHistoryNullTintTv.setVisibility(visibility);
        searchHistoryClearAllTv.setTextColor(ContextCompat.getColor(getActivity(), textColor));
        drawable = ContextCompat.getDrawable(getActivity(), clearDrawable);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        searchHistoryClearAllTv.setCompoundDrawables(drawable, null, null, null);
        searchHistoryClearAllTv.setCompoundDrawablePadding(CommonUtils.dp2px(6));
    }

    private void initRecyclerView() {
        mSearchHistroyAdapter = new SearchHistroyAdapter(R.layout.item_search_history, null);
        mSearchHistroyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                searchHistoryData(adapter, position);

            }
        });

        searchHistoryRv.setLayoutManager(new LinearLayoutManager(getContext()));

        searchHistoryRv.setAdapter(mSearchHistroyAdapter);


    }

    private void searchHistoryData(BaseQuickAdapter adapter, int position) {
        HistoryData historyData = (HistoryData) adapter.getData().get(position);
        mPresenter.addHistoryData(historyData.getData());
        editSearch.setText(historyData.getData().toString().trim());
        editSearch.setSelection(historyData.getData().length());
    }


    private void initRevealAnimation() {
        mCircularRevealAnimation = new CircularRevealAnimation();
        mCircularRevealAnimation.setAnimationListener(this);
        editSearch.getViewTreeObserver().addOnPreDrawListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_dialog;
    }

    @Override
    public void showHistoryDatda(List<HistoryData> historyDataList) {
        if (historyDataList == null || historyDataList.size() <= 0) {
            setHistoryTvStatus(true);
            return;
        }
        setHistoryTvStatus(false);
        Collections.reverse(historyDataList);
        mSearchHistroyAdapter.replaceData(historyDataList);

    }

    @Override
    public void showTopSearchData(List<TopSearchData> topSearchDataList) {

        mTopSearchDataList = topSearchDataList;
        topSearchFlowLayout.setAdapter(new TagAdapter<TopSearchData>(topSearchDataList) {
            @Override
            public View getView(FlowLayout parent, int position, TopSearchData topSearchData) {

                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.top_search_tv, parent, false);
                textView.setText(topSearchData.getName());
                textView.setBackgroundColor(CommonUtils.randomTagColor());
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                topSearchFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {

                        showTopSearchView(position);
                        return true;
                    }
                });

                return textView;
            }

        });

    }

    private void showTopSearchView(int position) {
        String name = mTopSearchDataList.get(position).getName().trim();
        mPresenter.addHistoryData(name);
        setHistoryTvStatus(false);
        editSearch.setText(name);
        editSearch.setSelection(name.length());
    }

    @Override
    public void judjeToSearchListActivity() {

        KeyBoardUtils.closeKeyboard(getContext(), editSearch);
        mCircularRevealAnimation.hide(editSearch, mView);
        JudgeUtils.startSearchListActivity(getActivity(), editSearch.getText().toString().trim());

    }

    @Override
    public void onShowAnimationEnd() {

        KeyBoardUtils.openKeyboard(getContext(), editSearch);
    }

    @Override
    public void onHideAnimationEnd() {

        editSearch.setText("");
        dismissAllowingStateLoss();

    }


    @Override
    public boolean onPreDraw() {

        editSearch.getViewTreeObserver().removeOnPreDrawListener(this);

        mCircularRevealAnimation.show(editSearch, mView);

        return true;
    }



    @OnClick({R.id.search_toolbar_back, R.id.search_history_clear_all_tv, R.id.search_floating_action_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_toolbar_back:

                onBackEvent();

                break;
            case R.id.search_history_clear_all_tv:
                clearHistory();
                break;
            case R.id.search_floating_action_btn:
                searchHistoryRv.smoothScrollToPosition(0);
                break;
        }
    }

    private void clearHistory() {
        setHistoryTvStatus(true);
        mPresenter.clearAllHistoryData();
        mSearchHistroyAdapter.replaceData(new ArrayList<>());
    }

    private void onBackEvent() {
        KeyBoardUtils.closeKeyboard(getContext(),editSearch);
        mCircularRevealAnimation.hide(editSearch,mView);
    }
}
