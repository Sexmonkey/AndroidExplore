package com.jerryzhu.androidexplore.ui.main.activity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.activity.BaseRootActivity;
import com.jerryzhu.androidexplore.bridge.main.SearchListBridge;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.presenter.main.SearchListPresenter;
import com.jerryzhu.androidexplore.ui.homepager.adapter.SearchListAdapter;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.JudgeUtils;
import com.jerryzhu.androidexplore.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;

public class SearchListActivity extends BaseRootActivity<SearchListPresenter> implements SearchListBridge.View {

    private int currentPage = 0;
    private int articlePosition = 0;
    private boolean isRefresh = false;

    @BindView(R.id.common_toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.normal_view)
    RecyclerView normalView;
    @BindView(R.id.search_list_smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.search_list_float_action_btn)
    FloatingActionButton floatActionBtn;
    private String mSearchText;
    private SearchListAdapter mSearchListAdapter;
    private List<FeedArticleData> mDataList;

    @Override
    protected void initDataAndEvent() {
        super.initDataAndEvent();

        mPresenter.getSearchList(currentPage,mSearchText,true);
        initRecyclerView();
        setRefresh();

        mPresenter.addRxBindingSubscribe(RxView.clicks(floatActionBtn)
                .throttleFirst(Constants.CLICK_TIME_AREA,TimeUnit.MILLISECONDS)
                .subscribe(o -> normalView.smoothScrollToPosition(0)));

    }

    private void initRecyclerView() {

        mDataList = new ArrayList<>();
        mSearchListAdapter = new SearchListAdapter(R.layout.item_search_pager, mDataList);
        mSearchListAdapter.isSearchPage();
        mSearchListAdapter.isNightMode(mPresenter.getNightModeState());
        mSearchListAdapter.setOnItemClickListener((adapter, view, position) -> startArticleActivity(view,position));
        mSearchListAdapter.setOnItemChildClickListener((adapter, view, position) -> onClickChildrenEvent(view,position));
        //设置 Header 为 贝塞尔雷达 样式
        smartrefreshlayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        smartrefreshlayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        normalView.setLayoutManager(new LinearLayoutManager(this));
        normalView.setHasFixedSize(true);
        normalView.setAdapter(mSearchListAdapter);

    }

    private void startArticleActivity(View view, int position) {
        if(mSearchListAdapter.getData().size() < position || mSearchListAdapter.getData().size() <= 0){
            return;
        }

        articlePosition = position;
        FeedArticleData articleData = mSearchListAdapter.getData().get(position);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(this,
                activityOptions,
                articleData.getId(),
                articleData.getTitle(),
                articleData.getLink(),
                articleData.isCollect(),
                false,false
        );
    }

    private void onClickChildrenEvent(View view, int position) {
        switch (view.getId()) {

            case R.id.item_home_pager_chapterName:
                clickChapterEvent(position);

                break;
            case R.id.item_home_pager_like_iv:
                likeEvent(position);

                break;
            case R.id.item_home_pager_tag_read_tv:
                clickTagEvent(position);

                break;

        }
    }

    private void clickChapterEvent(int position) {

        CommonUtils.showMessage(this,"clickChapterEvent...");
    }

    private void likeEvent(int position) {

        if(!mPresenter.getLoginStatus()){
            startActivity(new Intent(this,LoginActivity.class));
            CommonUtils.showMessage(this,getString(R.string.login_tint));
            return;
        }

        if(mSearchListAdapter.getData().size() < position || mSearchListAdapter.getData().size() <= 0){
            return;
        }

        FeedArticleData feedArticleData = mSearchListAdapter.getData().get(position);
        if(feedArticleData.isCollect()){
            mPresenter.cancelCollectArticle(position,feedArticleData);
        }else {
            mPresenter.addCollectArticle(position,feedArticleData);
        }

    }

    private void clickTagEvent(int position) {
        CommonUtils.showMessage(this,"clickTagEvent...");
    }

    @Override
    protected void initToolBar() {
        Bundle extras = getIntent().getExtras();
        mSearchText = (String)extras.get(Constants.SEARCH_TEXT);
        if(!TextUtils.isEmpty(mSearchText)){
            toolbarTitleTv.setText(mSearchText);
        }
        StatusBarUtil.setStatusColor(getWindow(),ContextCompat.getColor(this,R.color.search_status_bar_white),1f);
        if(mPresenter.getNightModeState()){

            commonToolbar.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_conner_bottom_blue));
            setToolBarView(R.color.white,R.drawable.ic_arrow_back_black_24dp);
        }else{
            StatusBarUtil.setStatusDarkColor(getWindow());
            commonToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            setToolBarView(R.color.title_black,R.drawable.ic_arrow_back_grey_24dp);

        }

        commonToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());


    }

    private void setToolBarView(int textColor, int drawable) {
        toolbarTitleTv.setTextColor(textColor);
        commonToolbar.setNavigationIcon(drawable);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    public void showSearchList(FeedArticleListData feedArticleListData) {

        mDataList = feedArticleListData.getDatas();

        if(isRefresh){
            mSearchListAdapter.replaceData(mDataList);
        }else {
            if(mDataList.size() > 0){

                mSearchListAdapter.addData(mDataList);
            }else {
                CommonUtils.showMessage(this,getString(R.string.load_more_no_data));
            }
        }

        showNormal();

    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mSearchListAdapter.setData(position,feedArticleData);
        CommonUtils.showMessage(this,getString(R.string.collect_success));

    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {

        mSearchListAdapter.setData(position,feedArticleData);
        CommonUtils.showMessage(this,getString(R.string.cancel_collect_success));


    }

    @Override
    public void showCollectSuccess() {

        showCollectResult(true,getString(R.string.collect_success));
    }



    @Override
    public void showCancelCollectSuccess() {

        showCollectResult(false,getString(R.string.cancel_collect_success));

    }

    private void showCollectResult(boolean collect, String collectTint) {
        FeedArticleData articleData = mSearchListAdapter.getData().get(articlePosition);
        articleData.setCollect(collect);
        mSearchListAdapter.setData(articlePosition,articleData);
        showToast(collectTint);
    }

    private void setRefresh() {

        smartrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                currentPage++;
                mPresenter.getSearchList(currentPage,mSearchText,false);
                smartrefreshlayout.finishLoadMore(1000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                currentPage = 0;
                mPresenter.getSearchList(currentPage,mSearchText,false);
                smartrefreshlayout.finishRefresh(1000);

            }
        });

    }


    @Override
    public void reload() {

        if (mPresenter != null){

            mPresenter.getSearchList(0,mSearchText,false);
        }

    }
}
