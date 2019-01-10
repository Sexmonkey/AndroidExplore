package com.jerryzhu.androidexplore.ui.hierarchy.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jerryzhu.androidexplore.core.bean.HierarchyPager.KnowledgeHierarchyData;

import java.util.List;

public class HierarchyPagerAdapter extends BaseQuickAdapter<KnowledgeHierarchyData,BaseViewHolder> {


    public HierarchyPagerAdapter(int layoutResId, @Nullable List<KnowledgeHierarchyData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeHierarchyData item) {

    }
}