package com.jerryzhu.androidexplore.di.component;

import com.jerryzhu.androidexplore.base.fragment.BaseDialogFragment;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseDialogFragmentComponent extends AndroidInjector<BaseDialogFragment> {

      @Subcomponent.Builder
      abstract class BaseBuilder extends AndroidInjector.Builder<BaseDialogFragment>{

      }
}
