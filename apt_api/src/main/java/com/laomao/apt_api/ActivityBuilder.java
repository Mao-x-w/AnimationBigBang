package com.laomao.apt_api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * User: laomao
 * Date: 2020/2/25
 * Time: 下午6:06
 */
public class ActivityBuilder {

    private Application application;
    private static final String ACTIVITY_BUILDER_POSTFIX="Builder";

    private Application.ActivityLifecycleCallbacks lifecycleCallbacks=new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            performInject(activity,savedInstanceState);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public static final ActivityBuilder INSTANCE = new ActivityBuilder();

    public void startActivity(Context context, Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);

    }

    public void init(Application application){
        if (this.application!=null)
            return;
        this.application=application;
        this.application.registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    private void performInject(Activity activity, Bundle savedInstanceState) {
        try {
            Class.forName(activity.getClass().getName()+ACTIVITY_BUILDER_POSTFIX).getDeclaredMethod("inject",Activity.class,Bundle.class).invoke(null,activity,savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
