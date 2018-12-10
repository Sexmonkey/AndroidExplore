package com.jerryzhu.androidexplore.component;

import android.app.Activity;
import android.os.Process;

import java.util.HashSet;
import java.util.Set;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */
public class ActivityCollector {

    private static ActivityCollector sActivityCollector;
    private Set<Activity> mActivitySet;

    public static synchronized ActivityCollector getInstance(){
        if (sActivityCollector == null) {
            sActivityCollector = new ActivityCollector();

        }

        return sActivityCollector;

    }

    public void addActivity(Activity activity){
        if (mActivitySet == null) {
            mActivitySet = new HashSet<Activity>();
        }

        mActivitySet.add(activity);

    }

    public void removeActivity(Activity activity){

        if (mActivitySet != null) {
            mActivitySet.remove(activity);
        }

    }

    public void exit(){

        if (mActivitySet != null) {
            synchronized (this){
                for (Activity activity : mActivitySet) {
                    activity.finish();
                }
            }

        }
//       退出应用
        Process.killProcess(Process.myPid());
        System.exit(0);

    }

}
