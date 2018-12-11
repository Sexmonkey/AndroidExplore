package com.jerryzhu.androidexplore.ui.main.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.MainBridge;
import com.jerryzhu.androidexplore.presenter.main.MainPresenter;
import com.jerryzhu.androidexplore.ui.hierarchy.fragment.HierarchyPagerFragment;
import com.jerryzhu.androidexplore.ui.homepager.fragment.HomePagerFragment;
import com.jerryzhu.androidexplore.ui.navigation.fragment.NavigationPagerFragment;
import com.jerryzhu.androidexplore.ui.project.fragment.ProjectPagerFragment;
import com.jerryzhu.androidexplore.ui.wx.fragment.WXPagerFragment;
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

    }

    @Override
    public void showSwitchNavigation() {

    }

    @Override
    public void showAutoLoginView() {

    }

    @Override
    public void showLogoutSuccess() {

    }


    private void initFragments() {

        mHierarchyPagerFragment = HierarchyPagerFragment.getInstance(null, null);
        mNavigationPagerFragment = NavigationPagerFragment.getInstance(null, null);
        mProjectPagerFragment = ProjectPagerFragment.getInstance(null, null);
        mWxPagerFragment = WXPagerFragment.getInstance(null, null);

        mFragments.add(mHierarchyPagerFragment);
        mFragments.add(mNavigationPagerFragment);
        mFragments.add(mProjectPagerFragment);
        mFragments.add(mWxPagerFragment);

    }


    private void init() {

        mPresenter.setCurrentPage(Constants.TYPE_MAIN_PAGER);
        initNavigationView();
        initBottomNavigation();
        initDrawerLayout();

    }

    private void initDrawerLayout() {

    }

    private void initBottomNavigation() {

    }

    private void initNavigationView() {



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

    @OnClick(R.id.main_float_action_btn)
    public void onViewClicked() {
    }
}
