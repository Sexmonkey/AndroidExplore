package com.jerryzhu.androidexplore.di.module;

import com.jerryzhu.androidexplore.di.component.BaseFragmentComponent;
import com.jerryzhu.androidexplore.ui.main.fragment.CollectFragment;
import com.jerryzhu.androidexplore.ui.main.fragment.SettingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {

//    @ContributesAndroidInjector(modules = CollectFragmentModule.class)
//    abstract CollectFragment contributeCollectFragmentInject();
//
//
//    @ContributesAndroidInjector(modules = SettingFragmentModule.class)
//    abstract SettingFragment contributeSettingFragmentInject();

}
