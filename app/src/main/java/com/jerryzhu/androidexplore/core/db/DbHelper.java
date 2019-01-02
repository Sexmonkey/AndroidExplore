package com.jerryzhu.androidexplore.core.db;

import com.jerryzhu.androidexplore.core.dao.HistoryData;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface DbHelper {
    List<HistoryData> addHistoryData(String data);
}
