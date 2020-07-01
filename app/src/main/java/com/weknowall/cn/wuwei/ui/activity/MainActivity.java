package com.weknowall.cn.wuwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_arouter_test.ARouterTestJumpActivity;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.model.User;
import com.weknowall.cn.wuwei.model.User2;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.ui.activity.launchMode.ActivityB;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.Headers;


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
            , R.id.round_viewGroup, R.id.douyin, R.id.transition_animation, R.id.hot_fix, R.id.url_jump
            , R.id.apt_demo, R.id.recycler_view_pager, R.id.kotlin_demo, R.id.launch_mode_demo, R.id.ipc_demo
            , R.id.open_vc, R.id.big_img, R.id.linked_list_demo, R.id.sort_demo, R.id.large_img_demo
            , R.id.proxy_demo, R.id.leak_canary_demo, R.id.custom_view_demo, R.id.builder_demo
            , R.id.serial_demo, R.id.android_q, R.id.saf, R.id.transparent_activity, R.id.test_animate
            , R.id.arouter_jump, R.id.arouter_jump_inner_app})
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
                break;
            case R.id.recycler_view_pager:
                startActivity(RecyclerViewPagerDemo.class);
                break;
            case R.id.kotlin_demo:
//                startActivity(KotlinDemoActivity.class);
//                KotlinDemoActivityBuilder.start(getContext(),20,"10000","张三");
                break;
            case R.id.apt_demo:
                startActivity(AptDemoActivity.class);
                break;
            case R.id.launch_mode_demo:
                // 通过非Activity的context启动Activity的时候。需要加flag: FLAG_ACTIVITY_NEW_TASK 和singleTask效果一样
                // 当我们通过Activity的context启动时，会取当前的Activity所在的栈进行判断，所以不需要加flag
                // 无论任何情况下都得加，因为无法判断当前的acitivity的栈信息。而加上那个flag就不判读了
//                getApplicationContext().startActivity(new Intent(getApplicationContext(),ActivityC.class));
                startActivity(new Intent(getApplicationContext(), ActivityB.class));
                break;
            case R.id.ipc_demo:
                startActivity(IpcDemoActivity.class);
                break;
            case R.id.open_vc:
                startActivity(OpenvcDemoActivity.class);
                break;
            case R.id.big_img:
                startActivity(BigImageActivity.class);
                break;
            case R.id.linked_list_demo:
                startActivity(LinkedListDemoActivity.class);
                break;
            case R.id.sort_demo:
                startActivity(SortDemoActivity.class);
                break;
            case R.id.large_img_demo:
                startActivity(LargeImageActivity.class);
                break;
            case R.id.proxy_demo:
                startActivity(ProxyActivity.class);
                break;
            case R.id.leak_canary_demo:
                startActivity(LeakCanaryActivity.class);
                break;
            case R.id.custom_view_demo:
                startActivity(CustomViewDemoActivity.class);
                break;
            case R.id.builder_demo:
                startActivity(BuilderDemoActivity.class);
                break;
            case R.id.serial_demo:
                HashMap<Integer, User> hashMap = new HashMap<>();
                User user = new User();
                user.setUserName("李四");
                hashMap.put(1, user);
                User2 user2 = new User2();
                startActivity(new Intent(getContext(), SerializeDemoActivity.class).putExtra("map", hashMap).putExtra("byte", user2));
                break;
            case R.id.android_q:
                startActivity(AndroidQDemoActivity.class);
                break;
            case R.id.transparent_activity:
                startActivity(TransparentActivity.class);
                break;
            case R.id.saf:
                startActivity(SAFDemoActivity.class);
                break;
            case R.id.test_animate:
                startActivity(AnimateDemoActivity.class);
                break;
            case R.id.arouter_jump:
                ARouter.getInstance().build("/test/testjump").navigation();
                break;
            case R.id.arouter_jump_inner_app:
                ARouter.getInstance().build("/app/activitya").navigation();
                break;
        }
    }
}
