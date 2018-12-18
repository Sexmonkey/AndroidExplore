package com.jerryzhu.androidexplore.ui.homepager.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.fragment.BaseRootFragment;
import com.jerryzhu.androidexplore.bridge.homepager.HomePagerBridge;
import com.jerryzhu.androidexplore.component.RxBus;
import com.jerryzhu.androidexplore.core.bean.mainpager.banner.BannerData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.event.AutoLoginEvent;
import com.jerryzhu.androidexplore.core.event.LoginEvent;
import com.jerryzhu.androidexplore.presenter.homepager.HomePagerPresenter;
import com.jerryzhu.androidexplore.ui.homepager.adapter.HomePageraAapter;
import com.jerryzhu.androidexplore.ui.main.activity.LoginActivity;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.GlideImageLoader;
import com.jerryzhu.androidexplore.utils.JudgeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class HomePagerFragment extends BaseRootFragment<HomePagerPresenter> implements HomePagerBridge.View {


    @BindView(R.id.main_pager_recycler_view)
    RecyclerView mHomePagerRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mSmartRefreshLayout;
    private boolean isRecreate;
    private List<FeedArticleData> mFeedArticleDataList;
    private HomePageraAapter mHomePageraAapter;
    private int mArticlePosition;
    private Banner mBanner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isRecreate = getArguments().getBoolean(Constants.ARG_PARAM1);
    }

    @Override
    protected void initView() {
        mFeedArticleDataList = new ArrayList<>();
        mHomePageraAapter = new HomePageraAapter(R.layout.item_home_pager, mFeedArticleDataList);
        mHomePageraAapter.setOnItemClickListener((adapter, view, position) -> {
            startArticleDetailPager(view,position);

        });
        mHomePageraAapter.setOnItemChildClickListener((adapter, view, position) -> {
            clickChildEvent(view,position);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        mHomePagerRecyclerView.setLayoutManager(linearLayoutManager);
        mHomePagerRecyclerView.setHasFixedSize(true);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.fragment_main_pager_banner, null);
        mBanner = linearLayout.findViewById(R.id.home_pager_banner);
        linearLayout.removeView(mBanner);
        mHomePageraAapter.addHeaderView(mBanner);
        mHomePagerRecyclerView.setAdapter(mHomePageraAapter);

    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {

            case R.id.item_home_pager_like_iv:
                likeEvent(position);

                break;
            case R.id.item_home_pager_chapterName:
                startSingleChapterKnowledgePager(position);

                break;
            case R.id.item_home_pager_tag_read_tv:
                clickTag(position);

            default:

                break;


        }
    }

    private void startSingleChapterKnowledgePager(int position) {
        CommonUtils.showMessage(_mActivity,"startSingleChapterKnowledgePager");
    }

    private void clickTag(int position) {

        CommonUtils.showMessage(_mActivity,"clickTag");

    }

    private void likeEvent(int position) {

        if(!mPresent.getLoginStatus()){
            startActivity(new Intent(_mActivity,LoginActivity.class));
            CommonUtils.showMessage(_mActivity,getString(R.string.login_tint));
            return;
        }
        if(mHomePageraAapter.getData().size() < position || mHomePageraAapter.getData().size() <= 0){
            return;
        }
        FeedArticleData feedArticleData = mHomePageraAapter.getData().get(position);

        if(feedArticleData.isCollect()){
            mPresent.cancelCollectArticle(position,feedArticleData);
        }else{
            mPresent.addCollectArticle(position,feedArticleData);
        }

    }

    /**
     * @param view
     * @param position
     */
    private void startArticleDetailPager(View view, int position) {
        if (mHomePageraAapter.getData().size() <= 0 || mHomePageraAapter.getData().size() < position){
            return;
        }
//      记录点击文章的位置，如果用户点击了收藏返回到此界面时能够显示正确的状态
        mArticlePosition = position;
        FeedArticleData feedArticleData = mHomePageraAapter.getData().get(position);
//      跳转动画
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));

        JudgeUtils.startArticleDetailActivity(
                _mActivity,
                activityOptions,
                feedArticleData.getId(),
                feedArticleData.getTitle(),
                feedArticleData.getLink(),
                feedArticleData.isCollect(),
                false,
                false);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_pager;
    }

    public static HomePagerFragment getInstance(boolean isRecreate, String params) {

        HomePagerFragment fragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, isRecreate);
        bundle.putString(Constants.ARG_PARAM2, params);
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    protected void initViewAndData() {
        super.initViewAndData();
        setRefreshAndLoadMoreListener();
        if(loggedAndNotRebuilt()){
            mPresent.loadHomePagerData();
        }else{
            mPresent.autoRefresh(true);
        }
        if(CommonUtils.isNetworkConnected()){
            showLoading();
        }
    }

    private boolean loggedAndNotRebuilt() {
        return !TextUtils.isEmpty(mPresent.getLoginAccount())
                && !TextUtils.isEmpty(mPresent.getLoginPassword())
                && !isRecreate;
    }

    private void setRefreshAndLoadMoreListener() {

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresent.autoRefresh(false);
            refreshLayout.finishRefresh(1000);

        });

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresent.loadMoreData();
            refreshLayout.finishLoadMore(1000);

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showAutoLoginSuccess() {
        if(isAdded()){
            CommonUtils.showSnackMessage(_mActivity,_mActivity.getString(R.string.login_success));
            RxBus.getDefault().send(new AutoLoginEvent());
        }
    }

    @Override
    public void showAutoLoginFail() {
        mPresent.setLoginStatus(false);
        RxBus.getDefault().send(new LoginEvent(false));
    }

    @Override
    public void showArticleList(FeedArticleListData feedArticleListData, boolean isRefresh) {

        if(mPresent.getCurrentPage() == Constants.TYPE_HOME_PAGER){
            mHomePagerRecyclerView.setVisibility(View.VISIBLE);
        }else{
            mHomePagerRecyclerView.setVisibility(View.INVISIBLE);
        }
        if (mHomePageraAapter == null) {
            return;
        }
        if(isRefresh){
            mFeedArticleDataList = feedArticleListData.getDatas();
            mHomePageraAapter.replaceData(mFeedArticleDataList);
        }else{
            mFeedArticleDataList.addAll(feedArticleListData.getDatas());
            mHomePageraAapter.addData(feedArticleListData.getDatas());
        }
        showNormal();
    }

    @Override
    public void showColloectArticleData(int position, FeedArticleListData feedArticleListData, FeedArticleData feedArticleData) {
         mHomePageraAapter.setData(position,feedArticleData);
         CommonUtils.showMessage(_mActivity,getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleListData feedArticleListData, FeedArticleData feedArticleData) {
          mHomePageraAapter.setData(position,feedArticleData);
          CommonUtils.showMessage(_mActivity,getString(R.string.cancel_collect_success));
    }

    /**
     * @param bannerDataList
     */
    @Override
    public void showBannerData(List<BannerData> bannerDataList) {
        ArrayList<String> imagePathList = new ArrayList<>();
        ArrayList<String> titleList = new ArrayList<>();
        for (int i = 0; i < bannerDataList.size(); i++) {
            imagePathList.add(bannerDataList.get(i).getImagePath());
            titleList.add(bannerDataList.get(i).getTitle());
        }

//        设置样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
//        设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
//        设置图片集合
        mBanner.setImages(imagePathList);
//        设置动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
//        设置标题集合
        mBanner.setBannerTitles(titleList);
//        设置自动播放
        mBanner.isAutoPlay(true);
//        设置轮播时间
        mBanner.setDelayTime(1500);
//        设置指示器显示位置
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                JudgeUtils.startArticleDetailActivity(_mActivity,null,0,
                        bannerDataList.get(position).getTitle(),
                        bannerDataList.get(position).getUrl(),
                        false,false,false);
            }
        });

        mBanner.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    public void jumpToTop() {
        if (mHomePagerRecyclerView != null) {
            mHomePagerRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showLoginView() {
        mPresent.getFeedArticleList(false);
    }

    @Override
    public void showLogoutView() {
        mPresent.getFeedArticleList(false);
    }

    @Override
    public void reload() {
        if(mSmartRefreshLayout != null && mPresent != null
                && mHomePagerRecyclerView.getVisibility() == View.VISIBLE
                && CommonUtils.isNetworkConnected()){

            mSmartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showCancelCollectSuccess() {
        super.showCancelCollectSuccess();
    }

    @Override
    public void showCollectSuccess() {
        super.showCollectSuccess();
    }
}
