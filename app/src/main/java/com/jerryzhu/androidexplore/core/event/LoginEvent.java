package com.jerryzhu.androidexplore.core.event;

public class LoginEvent {

    private boolean isLogined;

    public boolean isLogined() {
        return isLogined;
    }
    public void setLogined(boolean isLogined) {
        this.isLogined = isLogined;
    }
    public LoginEvent(boolean isLogined) {
        this.isLogined = isLogined;
    }
}
