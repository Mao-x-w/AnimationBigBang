package com.weknowall.cn.wuwei.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.app_presenter.dagger.modules.ActivityModule;
import com.weknowall.app_presenter.view.ILoadingView;
import com.weknowall.cn.wuwei.CustomApplication;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.dagger.components.ApplicationComponent;
import com.weknowall.cn.wuwei.utils.Logs;
import com.weknowall.cn.wuwei.utils.StatusBarUtils;
import com.weknowall.cn.wuwei.utils.UiHelper;

/**
 * User: laomao
 * Date: 2016-12-21
 * Time: 11-36
 */

public class BaseActivity extends AppCompatActivity implements ILoadingView{

    private UiHelper mUiHelper;
    protected Toolbar mToolbar;


    public Context getContext(){
        return this;
    }

    public void startActivity(Class<? extends BaseActivity> clazz){
        startActivity(new Intent(getContext(),clazz));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logs.d("BaseActivity:::::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logs.d("BaseActivity:::::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logs.d("BaseActivity:::::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logs.d("BaseActivity:::::onStop()");
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(v -> finish());
        }

        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtils.setColorForSwipeBack(this, Color.parseColor("#ffffff"),0);
        StatusBarUtils.StatusBarLightMode(this,true);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_no_slide);
    }


    protected void overrideClosePendingTransition() {
        overridePendingTransition(R.anim.anim_no_slide, R.anim.anim_slide_out_right);
    }



    @Override
    public void finish() {
        super.finish();
        overrideClosePendingTransition();
    }


    /**
     * Dagger application component
     */
    public ApplicationComponent getApplicationComponent() {
        return ((CustomApplication) getApplication()).getComponent();
    }

    /**
     * Dagger activity module
     */
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


    protected synchronized UiHelper getUiHelper() {
        synchronized (BaseActivity.class) {
            if (mUiHelper == null) {
                synchronized (BaseActivity.class) {
                    mUiHelper = new UiHelper(this);
                }
            }
        }
        return mUiHelper;
    }

    /**
     * 隐藏输入框
     */
    public void hideInput() {
        if (DeviceHelper.isInputDisplaying(getContext())) {
            DeviceHelper.toggleInput(getCurrentFocus(), false);
        }
    }


    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showToast(resId);
    }



    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onError(Class clazz) {

    }

    @Override
    public void onFinish() {

    }

    protected void showLoadingDialog() {
        getUiHelper().showLoadingDialog();
    }

    protected void dismissLoadingDialog() {
        getUiHelper().dismissLoadingDialog();
    }

    protected void showToast(String message) {
        getUiHelper().showToast(message);
    }

    protected void showToast(int resId) {
        getUiHelper().showToast(getString(resId));
    }
}
