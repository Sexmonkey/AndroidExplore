package com.jerryzhu.androidexplore.di.module;

import com.jerryzhu.androidexplore.di.component.BaseActivityComponent;
import com.jerryzhu.androidexplore.ui.main.activity.ArticleDetailActivity;
import com.jerryzhu.androidexplore.ui.main.activity.LoginActivity;
import com.jerryzhu.androidexplore.ui.main.activity.MainActivity;
import com.jerryzhu.androidexplore.ui.main.activity.SplashActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ContributesAndroidInjector(modules = {SplashActivityModule.class})
    abstract SplashActivity contributesSplashActivityInjector();

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector(modules = {ArticleDetailActivityModule.class})
    abstract ArticleDetailActivity contributesArticleDetailActivityInjector();

    @ContributesAndroidInjector(modules = {LoginActivityModule.class})
    abstract LoginActivity contributesLoginActivityInjector();

}
