package com.jerryzhu.androidexplore.core.db;

import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.core.dao.DaoSession;
import com.jerryzhu.androidexplore.core.dao.HistoryData;
import com.jerryzhu.androidexplore.core.dao.HistoryDataDao;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

public class DbHelperImpl implements DbHelper {

    private final DaoSession mDaoSession;
    private String mData;
    private List<HistoryData> mHistoryDataList;
    private HistoryData mHistoryData;

    @Inject
    public DbHelperImpl() {

        mDaoSession = AndroidExploreApp.getInstance().getDaoSession();

    }

    @Override
    public List<HistoryData> addHistoryData(String data) {

        mData = data;

        getHistoryDataList();
        createHistoryData(data);

        if(historyDataForward()){

            return mHistoryDataList;

        }

        if(mHistoryDataList.size() < 10){

            getHistoryDao().insertInTx(mHistoryData);

        }else{
            mHistoryDataList.remove(0);
            mHistoryDataList.add(mHistoryData);
            getHistoryDao().deleteAll();
            getHistoryDao().insertInTx(mHistoryDataList);
        }

        return mHistoryDataList;
    }

    @Override
    public void clearHistoryData() {

        mDaoSession.getHistoryDataDao().deleteAll();

    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return mDaoSession.getHistoryDataDao().loadAll();
    }

    /**
     * 历史数据前移
     *
     * @return 返回true表示查询的数据已存在，只需将其前移到第一项历史记录，否则需要增加新的历史记录
     */
    private  boolean historyDataForward(){

        Iterator<HistoryData> iterator = mHistoryDataList.iterator();
        while (iterator.hasNext()){
            HistoryData historyDataRe = iterator.next();
            if(historyDataRe.getData().equals(mData)){
                mHistoryDataList.remove(historyDataRe);
                mHistoryDataList.add(mHistoryData);
                getHistoryDao().deleteAll();
                getHistoryDao().insertInTx(mHistoryDataList);

                return true;

            }

        }

        return false;

    }

    private void createHistoryData(String data){

        mHistoryData = new HistoryData();
        mHistoryData.setData(data);
        mHistoryData.setDate(System.currentTimeMillis());

    }

    private void getHistoryDataList() {
        mHistoryDataList = getHistoryDao().loadAll();
    }

    private HistoryDataDao getHistoryDao() {
       return mDaoSession.getHistoryDataDao();
    }
}
