package com.jerryzhu.androidexplore.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.MainBridge;
import com.jerryzhu.androidexplore.presenter.main.MainPresenter;
import com.jerryzhu.androidexplore.ui.hierarchy.fragment.HierarchyPagerFragment;
import com.jerryzhu.androidexplore.ui.homepager.fragment.HomePagerFragment;
import com.jerryzhu.androidexplore.ui.main.fragment.CollectFragment;
import com.jerryzhu.androidexplore.ui.main.fragment.SettingFragment;
import com.jerryzhu.androidexplore.ui.navigation.fragment.NavigationPagerFragment;
import com.jerryzhu.androidexplore.ui.project.fragment.ProjectPagerFragment;
import com.jerryzhu.androidexplore.ui.wx.fragment.WXPagerFragment;
import com.jerryzhu.androidexplore.utils.BottomNavigationViewHelper;
import com.jerryzhu.androidexplore.utils.CommonAlertDialog;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.StatusBarUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainBridge.View {

    private ArrayList<Fragment> mFragments;
    private HomePagerFragment mHomePagerFragment;
    private HierarchyPagerFragment mHierarchyPagerFragment;
    private NavigationPagerFragment mNavigationPagerFragment;
    private ProjectPagerFragment mProjectPagerFragment;
    private WXPagerFragment mWxPagerFragment;
    private int lastFragmentIndex;

    @BindView(R.id.common_toolbar_title_tv)
    TextView commonToolbarTitleTv;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.main_float_action_btn)
    FloatingActionButton mainFloatActionBtn;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fragment_group)
    FrameLayout fragmentGroup;
    private TextView mLoginTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new ArrayList<>();
        if(savedInstanceState == null){
            mPresenter.setNightModeState(false);
            initPager(false,Constants.TYPE_MAIN_PAGER);
        }else{
            bottomNavigationView.setSelectedItemId(R.id.tab_main_pager);
            initPager(true,Constants.TYPE_SETTING);
        }

    }

    private void initPager(boolean isRecreate, int position) {
        mHomePagerFragment = HomePagerFragment.getInstance(isRecreate, null);
        mFragments.add(mHomePagerFragment);
        initFragments();
        init();
        switchFragment(position);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {

        setSupportActionBar(commonToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setDisplayShowTitleEnabled(false);
        commonToolbarTitleTv.setText(R.string.home_pager);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_blue), 1f);
        commonToolbar.setNavigationOnClickListener(view -> onBackPressedSupport());
    }



    @Override
    protected void initDataAndEvent() {

    }


    @Override
    public void showSwitchProject() {
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.tab_project);
        }

    }

    @Override
    public void showSwitchNavigation() {

        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.tab_project);
        }

    }

    @Override
    public void showAutoLoginView() {

        showLoginView();

    }

    @Override
    public void showLogoutSuccess() {

    }

    @Override
    public void showLoginView() {

        if(navView == null){
            return;
        }
        mLoginTv = navView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        mLoginTv.setText(mPresenter.getLoginAccount());
        mLoginTv.setOnClickListener(null);
        navView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);

    }

    @Override
    public void showLogoutView() {
        mLoginTv.setText(R.string.login_in);
        mLoginTv.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
        if (navView == null) {
            return;
        }
        navView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
    }

    private void initFragments() {

        mHierarchyPagerFragment = HierarchyPagerFragment.getInstance(null, null);
        mNavigationPagerFragment = NavigationPagerFragment.getInstance(null, null);
        mProjectPagerFragment = ProjectPagerFragment.getInstance(null, null);
        mWxPagerFragment = WXPagerFragment.getInstance(null, null);
        SettingFragment settingFragment = SettingFragment.getInstance(null, null);
        CollectFragment collectFragment = CollectFragment.getInstance(null, null);

        mFragments.add(mHierarchyPagerFragment);
        mFragments.add(mNavigationPagerFragment);
        mFragments.add(mProjectPagerFragment);
        mFragments.add(mWxPagerFragment);
        mFragments.add(settingFragment);
        mFragments.add(collectFragment);

    }


    private void init() {

        mPresenter.setCurrentPage(Constants.TYPE_MAIN_PAGER);
        initNavigationView();
        initBottomNavigation();
        initDrawerLayout();

    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, commonToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = drawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };

        actionBarDrawerToggle.syncState();

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }

    private void initBottomNavigation() {
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.tab_main_pager:

                    loadFragment(getString(R.string.home_pager),0,mHomePagerFragment,Constants.TYPE_MAIN_PAGER);

                    break;
                case R.id.tab_knowledge_hierarchy:
                    loadFragment(getString(R.string.knowledge_hierarchy),1,mHierarchyPagerFragment,Constants.TYPE_KNOWLEDGE);

                    break;
                case R.id.tab_wx_article:
                    loadFragment(getString(R.string.wx_article),2,mWxPagerFragment,Constants.TYPE_WX_ARTICLE);
                    break;
                case R.id.tab_navigation:
                    loadFragment(getString(R.string.navigation),3,mNavigationPagerFragment,Constants.TYPE_NAVIGATION);
                    break;
                case R.id.tab_project:
                    loadFragment(getString(R.string.project),4,mProjectPagerFragment,Constants.TYPE_PROJECT);
                    break;

                default:

                    break;

            }
            return true;
        });

    }

    private void loadFragment(String title, int position,Fragment fragment, int pagerType) {

        commonToolbarTitleTv.setText(title);
        switchFragment(position);
        mPresenter.setCurrentPage(pagerType);

    }

    private void initNavigationView() {
        mLoginTv = navView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        if(mPresenter.getLoginStatus()){
            showLoginView();
        }else{
            showLogoutView();
        }
        navView.getMenu().findItem(R.id.nav_item_wan_android).setOnMenuItemClickListener(menuItem -> {
            showMainPager();
            return true;
        });
        navView.getMenu().findItem(R.id.nav_item_my_collect).setOnMenuItemClickListener(menuItem -> {
            if(mPresenter.getLoginStatus()){
                showCollectFragment();

            }else{
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                CommonUtils.showMessage(MainActivity.this,"请先登录。。。");
            }
            return true;
        });

        navView.getMenu().findItem(R.id.nav_item_setting).setOnMenuItemClickListener(menuItem -> {
            showSettingFragment();
            return true;
        });

        navView.getMenu().findItem(R.id.nav_item_about_us).setOnMenuItemClickListener(menuItem -> {

            startActivity(new Intent(MainActivity.this,AboutUsActivity.class));

            return true;
        });

        navView.getMenu().findItem(R.id.nav_item_logout).setOnMenuItemClickListener(menuItem -> {
            logOut();
            return true;
        });

    }

    private void logOut() {
        CommonAlertDialog.newInstance().showDialog(this,
                getString(R.string.login_tint),
                getString(R.string.ok),
                getString(R.string.no),
                view -> mPresenter.Logout(),
                view -> CommonAlertDialog.newInstance().cancelDialog(true));

    }

    private void showSettingFragment() {

        commonToolbarTitleTv.setText(getString(R.string.setting));
        switchFragment(Constants.TYPE_SETTING);
        drawerLayout.closeDrawers();

    }


    private void showCollectFragment() {
        commonToolbarTitleTv.setText(getString(R.string.collect));
        switchFragment(Constants.TYPE_COLLECT);
        drawerLayout.closeDrawers();

    }

    private void showMainPager() {

        commonToolbarTitleTv.setText(getString(R.string.home_pager));
        mainFloatActionBtn.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.tab_main_pager);
        drawerLayout.closeDrawers();

    }

    private void switchFragment(int position) {
        if(position >= Constants.TYPE_SETTING){
            mainFloatActionBtn.setVisibility(View.INVISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }else {
            mainFloatActionBtn.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }

        if(position > mFragments.size()){
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastFragmentIndex);
        lastFragmentIndex = position;
        ft.hide(lastFragment);
        if(!targetFragment.isAdded()){
            getSupportFragmentManager().beginTransaction().remove(targetFragment).commitAllowingStateLoss();
            ft.add(R.id.fragment_group,targetFragment);
        }
        ft.show(targetFragment);
        ft.commitAllowingStateLoss();

    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if(getSupportFragmentManager().getBackStackEntryCount() > 1){
            pop();
        }else{
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_usage:
                Toast.makeText(this,"action_usage",Toast.LENGTH_LONG).show();

                break;
            case R.id.action_search:
                Toast.makeText(this,"action_search",Toast.LENGTH_LONG).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonAlertDialog.newInstance().cancelDialog(true);
    }

    @OnClick(R.id.main_float_action_btn)
    public void onViewClicked() {
        jumpToTop();

    }

    private void jumpToTop() {
        Toast.makeText(this,"float_action_btn",Toast.LENGTH_LONG).show();
    }
}
