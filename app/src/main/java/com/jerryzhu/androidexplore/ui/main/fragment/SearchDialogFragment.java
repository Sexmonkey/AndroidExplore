package com.jerryzhu.androidexplore.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.fragment.BaseDialogFragment;
import com.jerryzhu.androidexplore.bridge.main.SearchDialogBridge;
import com.jerryzhu.androidexplore.core.bean.mainpager.search.TopSearchData;
import com.jerryzhu.androidexplore.core.dao.HistoryData;
import com.jerryzhu.androidexplore.presenter.main.SearchDialogPresenter;
import com.jerryzhu.androidexplore.ui.main.adapter.SearchHistroyAdapter;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.widget.CircularRevealAnimation;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.Unbinder;

public class SearchDialogFragment extends BaseDialogFragment<SearchDialogPresenter> implements SearchDialogBridge.View, CircularRevealAnimation.AnimationListener {


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
    Unbinder unbinder;

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
                if(TextUtils.isEmpty(editSearch.getText().toString())){
                    searchTintTv.setText(AndroidExploreApp.getInstance().getString(R.string.search_null_tint));
                }else{
                    searchTintTv.setText("");
                }

            }
        });

    }

    private void initRecyclerView() {
        SearchHistroyAdapter searchHistroyAdapter = new SearchHistroyAdapter(R.layout.item_search_history, null);
        searchHistroyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                searchHistoryData(adapter,position);

            }
        });

        searchHistoryRv.setLayoutManager(new LinearLayoutManager(getContext()));

        searchHistoryRv.setAdapter(searchHistroyAdapter);


    }

    private void searchHistoryData(BaseQuickAdapter adapter, int position) {
        HistoryData historyData = (HistoryData)adapter.getData().get(position);
        mPresenter.addHistoryData(historyData.getData());
        editSearch.setText(historyData.getData().toString().trim());
        editSearch.setSelection(historyData.getData().length());
    }


    private void initRevealAnimation() {
        CircularRevealAnimation circularRevealAnimation = new CircularRevealAnimation();
        circularRevealAnimation.setAnimationListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_dialog;
    }

    @Override
    public void showHistoryDatda(List<HistoryData> historyDataList) {

    }

    @Override
    public void showTopSearchData(List<TopSearchData> topSearchDataList) {

    }

    @Override
    public void judjeToSearchListActivity() {

        CommonUtils.showMessage(getActivity(),"judjeToSearchListActivity...");

    }

    @Override
    public void onShowAnimationEnd() {

    }

    @Override
    public void onHideAnimationEnd() {

    }


}
