package com.jerryzhu.androidexplore.core.prefs;



public interface PreferenceHelper {

    /**
     * @param account
     */
    void setLoginAccount(String account);

    /**
     * @param password
     */
    void setLoginPassword(String password);


    /**
     * @return account
     */
    String getLoginAccount();


    /**
     * @return password
     */
    String getPassword();

    /**
     * @param status
     */
    void setLoginStatus(int status);

    /**
     * @return status
     */
    int getLoginStatus();

    /**
     * @param domain
     * @param cookie
     */
    void setCookie(String domain , String cookie);

    /**
     * @return cookie
     */
    String getCookie(String domain);

    /**
     * @param page
     */
    void setCurrentPage(int page);

    /**
     * @return page
     */
    int getCurrentPage();

    /**
     * @param position
     */
    void setProjectCurrentPage(int position);

    /**
     * @return position
     */
    int getProjectCurrentPage();

    /**
     * @return auto cache state
     */
    boolean getAutoCacheState();

    /**
     * @return night mode state
     */
    boolean getNightModeState();

    /**
     * @return no image state
     */
    boolean getNoImagestate();

    /**
     * @param state
     */
    void setAutoCacheState(boolean state);


    /**
     * @param state
     */
    void setNightModeState(boolean state);


    /**
     * @param state
     */
    void setNoImageState(boolean state);

}
