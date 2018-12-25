package com.jerryzhu.androidexplore.di.module;

import com.jerryzhu.androidexplore.di.component.BaseDialogFragmentComponent;
import com.jerryzhu.androidexplore.ui.main.fragment.UsageDialogFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseDialogFragmentComponent.class)
public abstract class AbstractAllDialogFragmentModule {


    @ContributesAndroidInjector(modules = {UsageDialogFragmentModule.class})
    abstract UsageDialogFragment contributesUsageDialogFragmentInjector();
}
