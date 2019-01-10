package com.jerryzhu.androidexplore.ui.hierarchy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.fragment.BaseRootFragment;
import com.jerryzhu.androidexplore.bridge.HierarchyPager.HierarchyPagerBridge;
import com.jerryzhu.androidexplore.core.bean.HierarchyPager.KnowledgeHierarchyData;
import com.jerryzhu.androidexplore.presenter.HierarchyPager.HierarchyPagerPresenter;
import com.jerryzhu.androidexplore.ui.hierarchy.adapter.HierarchyPagerAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HierarchyPagerFragment extends BaseRootFragment<HierarchyPagerPresenter> implements HierarchyPagerBridge.View {


    @BindView(R.id.normal_view)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.hierarchy_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.knowledge_hierarchy_enter_iv)
    ImageView mEnterImageView;
    @BindView(R.id.item_knowledge_hierarchy_title)
    TextView mTitleTextView;
    @BindView(R.id.item_knowledge_hierarchy_content)
    TextView mContentTextView;

    private List<KnowledgeHierarchyData> mKnowledgeHierarchyDataList;
    private HierarchyPagerAdapter mHierarchyPagerAdapter;

    @Override
    protected void initViewAndData() {
        super.initViewAndData();
        setRefresh();

    }

    @Override
    protected void initView() {

        initRecyclerView();

    }

    private void initRecyclerView() {
        mHierarchyPagerAdapter = new HierarchyPagerAdapter(R.layout.item_fragment_hierarchy_pager, mKnowledgeHierarchyDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(false);
        mHierarchyPagerAdapter.setOnItemClickListener((adapter, view, position) -> startDetailActivity(view, position));
        mRecyclerView.setAdapter(mHierarchyPagerAdapter);
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setColorSchemeColors(getResources().getColor(R.color.blue_btn)));
        mSmartRefreshLayout.setRefreshFooter(new FalsifyFooter(getContext()));
    }

    private void startDetailActivity(View view, int position) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hierarchy_konwledge;
    }


    public static HierarchyPagerFragment getInstance(String params1, String params2) {

        HierarchyPagerFragment fragment = new HierarchyPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARG_PARAM1, params1);
        bundle.putString(Constants.ARG_PARAM2, params2);
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList) {

    }

    private void setRefresh() {

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

    }

}
