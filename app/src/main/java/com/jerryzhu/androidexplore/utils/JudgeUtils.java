package com.jerryzhu.androidexplore.utils;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import com.jerryzhu.androidexplore.ui.main.activity.ArticleDetailActivity;



public class JudgeUtils {

    public static void startArticleDetailActivity(Context mActivity, ActivityOptions activityOptions, int id, String articleTitle,
                                                  String articleLink, boolean isCollect,
                                                  boolean isCollectPage,boolean isCommonSite) {
        mActivity.startActivity(new Intent(mActivity,ArticleDetailActivity.class));


    }



}
