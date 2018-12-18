package com.jerryzhu.androidexplore.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.ArticleDetailBridge;
import com.jerryzhu.androidexplore.component.RxBus;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.event.CollectEvent;
import com.jerryzhu.androidexplore.presenter.main.ArticleDetailPresenter;
import com.jerryzhu.androidexplore.utils.StatusBarUtil;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailBridge.View {

    @BindView(R.id.article_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.article_detail_web_view)
    FrameLayout mWebContent;

    private Bundle mBundleData;

    private String articleLink;
    private int article_id;
    private String article_title;
    private boolean is_Collect;
    private boolean is_Collect_page;
    private boolean is_Common_Site;
    private AgentWeb mAgentWeb;
    private MenuItem mMenuItem;

    @Override
    protected void initDataAndEvent() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(articleLink);

    }

    @Override
    protected void initToolBar() {
        getIntentData();
        mToolbar.setTitle(Html.fromHtml(article_title));
        setSupportActionBar(mToolbar);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this,mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_Collect){
                    RxBus.getDefault().send(new CollectEvent(false));
                }else{
                    RxBus.getDefault().send(new CollectEvent(true));
                }

                ArticleDetailActivity.this.supportBackPress();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mBundleData = getIntent().getExtras();
        assert mBundleData != null;
        is_Common_Site = (Boolean)mBundleData.get(Constants.IS_COMMON_SITE);
        if(is_Common_Site){
            getMenuInflater().inflate(R.menu.menu_article,menu);
        }else{
            unCommonSite(menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_share:

                shareEvent();

                break;
            case R.id.item_system_browser:

                systemBrowerEvent();

                break;
            case R.id.item_collect:

                collectEvent();

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void collectEvent() {

    }

    private void systemBrowerEvent() {


    }

    private void unCommonSite(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_common,menu);
        mMenuItem = menu.findItem(R.id.item_collect);

        if(is_Collect){
            mMenuItem.setTitle(getString(R.string.collect));
            mMenuItem.setIcon(R.mipmap.ic_toolbar_like_p);
        }else {
            mMenuItem.setTitle(getString(R.string.cancel_collect));
            mMenuItem.setIcon(R.mipmap.ic_toolbar_like_n);
        }

    }

    private void supportBackPress() {

        if(getSupportFragmentManager().getBackStackEntryCount() > 1){
            pop();
        }else{
            supportFinishAfterTransition();
        }

    }


    private void getIntentData() {
        mBundleData = getIntent().getExtras();
        if(null != mBundleData){
            article_id = (int) mBundleData.get(Constants.ARTICLE_ID);
            article_title = (String) mBundleData.get(Constants.ARTICLE_TITLE);
            article_title = (String) mBundleData.get(Constants.ARTICLE_TITLE);
            is_Collect = (Boolean) mBundleData.get(Constants.ARTICLE_LINK);
            is_Collect_page = (Boolean) mBundleData.get(Constants.IS_COLLECT_PAGE);
            is_Common_Site = (Boolean) mBundleData.get(Constants.IS_COMMON_SITE);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    public void showCollectArticleData(FeedArticleListData feedArticleListData) {



    }

    @Override
    public void showCancelCollectArticleData(FeedArticleListData feedArticleListData) {

    }

    @Override
    public void shareEvent() {

    }

    @Override
    public void shareError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
    }
}
