package com.weknowall.cn.wuwei.utils.AppFrontBack;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.weknowall.cn.wuwei.utils.Logs;

/**
 * User: laomao
 * Date: 2020/6/8
 * Time: 11:35 AM
 */
public class AppFrontBackHelper {

    private OnAppStatusListener mListener;

    public AppFrontBackHelper() {
    }

    public void register(Application application, OnAppStatusListener listener) {
        mListener = listener;
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public void unRegister(Application application) {
        application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {

        // 打开的Activity数量统计
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;

            // 从0变为1说明是从后台切到前台
            if (activityStartCount == 1) {
                if (mListener != null)
                    mListener.onFront();
            }

            Logs.d("ActivityLifecycleCallbacks:::::onActivityStarted()");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Logs.d("ActivityLifecycleCallbacks:::::onActivityResumed()");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Logs.d("ActivityLifecycleCallbacks:::::onActivityPaused()");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            // 从1变为0说明是从前台到后台
            if (activityStartCount == 0) {
                if (mListener != null)
                    mListener.onBack();
            }
            Logs.d("ActivityLifecycleCallbacks:::::onActivityStopped()");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public interface OnAppStatusListener {
        void onFront();

        void onBack();
    }

}
