package com.jerryzhu.androidexplore.core.event;

public class CollectEvent {

    private boolean isCancelCollect;

    public void setCancelCollect(boolean cancelCollect) {
        isCancelCollect = cancelCollect;
    }

    public boolean isCancelCollect() {
        return isCancelCollect;
    }

    public CollectEvent(boolean isCancelCollect) {
        this.isCancelCollect = isCancelCollect;
    }
}
