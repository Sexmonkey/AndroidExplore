package com.jerryzhu.androidexplore.core.db;

import com.jerryzhu.androidexplore.core.dao.HistoryData;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface DbHelper {
    /**
     * 增加历史数据
     *
     * @param data  added string
     * @return  List<HistoryData>
     */
    List<HistoryData> addHistoryData(String data);

    /**
     * Clear search history data
     */
    void clearHistoryData();

    /**
     * Load all history data
     *
     * @return List<HistoryData>
     */
    List<HistoryData> loadAllHistoryData();
}
