package com.jerryzhu.androidexplore.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.ui.main.activity.ArticleDetailActivity;
import com.jerryzhu.androidexplore.ui.main.activity.SearchListActivity;

public class JudgeUtils {

    public static void startArticleDetailActivity(Context activity, ActivityOptions activityOptions, int id, String articleTitle,
                                                  String articleLink, boolean isCollect,
                                                  boolean isCollectPage,boolean isCommonSite) {

        Intent intent = new Intent(activity,ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_ID,id);
        intent.putExtra(Constants.ARTICLE_TITLE,articleTitle);
        intent.putExtra(Constants.ARTICLE_LINK,articleLink);
        intent.putExtra(Constants.IS_COLLECT,isCollect);
        intent.putExtra(Constants.IS_COLLECT_PAGE,isCollectPage);
        intent.putExtra(Constants.IS_COMMON_SITE,isCommonSite);

        if(activityOptions != null && Build.MANUFACTURER.contains("samsung") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            activity.startActivity(intent,activityOptions.toBundle());
        }else{
            activity.startActivity(intent);
        }
    }

    public static void startSearchListActivity(Activity activity, String searchText) {

        Intent intent = new Intent(activity,SearchListActivity.class);
        intent.putExtra(Constants.SEARCH_TEXT,searchText);
        activity.startActivity(intent);

    }
}
