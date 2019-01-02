package com.jerryzhu.androidexplore.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.core.dao.HistoryData;
import com.jerryzhu.androidexplore.utils.CommonUtils;

import java.util.List;

public class SearchHistroyAdapter extends BaseQuickAdapter<HistoryData,BaseViewHolder> {

    public SearchHistroyAdapter(int layoutResId, @Nullable List<HistoryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryData historyData) {

        helper.setText(R.id.item_search_history_tv,historyData.getData());
        helper.setTextColor(R.id.item_search_history_tv,CommonUtils.randomColor());
        helper.addOnClickListener(R.id.item_search_history_tv);

    }
}
