package com.jerryzhu.androidexplore.di.component;

import com.jerryzhu.androidexplore.base.fragment.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseFragmentComponent extends AndroidInjector<BaseFragment> {

    @Subcomponent.Builder
     abstract class BaseBuilder extends AndroidInjector.Builder<BaseFragment>{

     }

}
