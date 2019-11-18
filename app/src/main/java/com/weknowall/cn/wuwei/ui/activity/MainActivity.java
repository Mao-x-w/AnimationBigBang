package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 11-24
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_mvp_test)
    Button mMvpTest;
    @BindView(R.id.main_swipe_delete)
    Button mSwipeDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.main_mvp_test, R.id.main_swipe_delete, R.id.main_bezier_curve
            , R.id.main_coordinator_layout, R.id.main_crop_image, R.id.web_view, R.id.sonic_web_view
            , R.id.thread_communicate, R.id.intent_service_demo, R.id.rxjava_demo, R.id.synchronized_demo
            , R.id.round_viewGroup, R.id.douyin,R.id.transition_animation,R.id.hot_fix,R.id.url_jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_mvp_test:
                startActivity(GitUsersActivity.class);
                break;
            case R.id.main_swipe_delete:
                startActivity(SwipeDeleteRecyclerViewActivtiy.class);
                break;
            case R.id.main_bezier_curve:
                startActivity(BezierCurveActivity.class);
                break;
            case R.id.main_coordinator_layout:
                startActivity(CoordinatorLayoutActivity.class);
                break;
            case R.id.main_crop_image:
                startActivity(CropImageActivity.class);
                break;
            case R.id.web_view:
                startActivity(WebViewActivity.class);
                break;
            case R.id.sonic_web_view:
                startActivity(SonicWebViewActivity.class);
                break;
            case R.id.thread_communicate:
                startActivity(ThreadDemoActivity.class);
                break;
            case R.id.intent_service_demo:
                startActivity(IntentServiceDemoActivity.class);
                break;
            case R.id.rxjava_demo:
                startActivity(RxjavaDemoActivity.class);
                break;
            case R.id.synchronized_demo:
                startActivity(SynchronizedDemoActivity.class);
                break;
            case R.id.round_viewGroup:
                startActivity(RoundViewGroupActivity.class);
                break;
            case R.id.douyin:
                startActivity(DouYinActivity.class);
                break;
            case R.id.transition_animation:
                startActivity(CrossActivitySceneAnimationActivity.class);
                break;
            case R.id.hot_fix:
                startActivity(HotFixDemoActivity.class);
                break;
            case R.id.url_jump:
                startActivity(UrlJumpActivity.class);
        }
    }
}
