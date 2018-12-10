package com.jerryzhu.androidexplore.core.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */

@Entity
public class HistoryData {

    @Id(autoincrement = true)
    private Long id;

    private long date;
    private String data;

    @Generated(hash = 1371145256)
    public HistoryData(Long id, long date, String data) {
        this.id = id;
        this.date = date;
        this.data = data;
    }
    @Generated(hash = 422767273)
    public HistoryData() {
    }
    public Long getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getData() {
        return data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setData(String data) {
        this.data = data;
    }
}
