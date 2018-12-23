package com.jerryzhu.androidexplore.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Method;

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

        WebView webView = mAgentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        if(mPresenter.getNoImageState()){
            settings.setBlockNetworkImage(true);
        }else{
            settings.setBlockNetworkImage(false);
        }

        if(mPresenter.getAutoCacheState()){
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);

            if(CommonUtils.isNetworkConnected()){
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }else{
            settings.setAppCacheEnabled(false);
            settings.setDomStorageEnabled(false);
            settings.setDatabaseEnabled(false);
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        settings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        settings.setUseWideViewPort(true);
        //缩放值屏幕大小
        settings.setLoadWithOverviewMode(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

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
        is_Common_Site = (boolean)mBundleData.get(Constants.IS_COMMON_SITE);
        if(is_Common_Site){
            getMenuInflater().inflate(R.menu.menu_article_common,menu);
        }else{
            unCommonSite(menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_share:
                mPresenter.shareEventPermissionVerify(new RxPermissions(this));
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

        if(mPresenter.getLoginStatus()){
            if(is_Collect){
                mPresenter.cancelCollectArticle(article_id);
            }else{
                if (is_Collect_page){

                    mPresenter.cancleCollectPageArticle(article_id);

                }else{
                    mPresenter.addCollectArticle(article_id);
                }
            }
        }else{
            CommonUtils.showMessage(this,getString(R.string.login_tint));
            startActivity(new Intent(this,LoginActivity.class));
        }

    }

    private void systemBrowerEvent() {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(articleLink)));
    }

    private void unCommonSite(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article,menu);
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
            articleLink = (String) mBundleData.get(Constants.ARTICLE_LINK);
            is_Collect = (Boolean) mBundleData.get(Constants.IS_COLLECT);
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
            is_Collect = true;
            mMenuItem.setTitle(getString(R.string.cancel_collect));
            mMenuItem.setIcon(R.mipmap.ic_toolbar_like_p);
            CommonUtils.showMessage(this,getString(R.string.collect_success));

    }

    @Override
    public void showCancelCollectArticleData(FeedArticleListData feedArticleListData) {
        is_Collect = false;
        if(!is_Collect_page){
            mMenuItem.setTitle(getString(R.string.collect));
        }
        mMenuItem.setIcon(R.mipmap.ic_toolbar_like_n);
        CommonUtils.showMessage(this,getString(R.string.cancel_collect_success));

    }

    @Override
    public void shareEvent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_type_url, getString(R.string.app_name), article_title, articleLink));
        intent.setType("text/plain");
        startActivity(intent);

    }

    @Override
    public void shareError() {

        CommonUtils.showSnackMessage(this,getString(R.string.write_permission_not_allowed));

    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu Menu
     * @return menu if opened
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (Constants.MENU_BUILDER.equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
