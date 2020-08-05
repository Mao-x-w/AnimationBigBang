package com.weknowall.cn.wuwei.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.ui.fragement.PersonalCenterFragment;
import com.weknowall.cn.wuwei.widget.image.AvatarImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-04-01
 * Time: 15-44
 */

public class CoordinatorLayoutActivity extends BaseActivity {

    @BindView(R.id.personal_center_original_user_avatar)
    AvatarImageView mOriginalUserAvatar;
    @BindView(R.id.personal_center_user_avatar)
    AvatarImageView mUserAvatar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.personal_center_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.personal_center_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.personal_center_original_user_title_root)
    RelativeLayout mTitleRoot;

    public static final String RX_BUS_SCROLL_TOP = "st";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);
        RxBus.get().register(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(RX_BUS_SCROLL_TOP)}, thread = EventThread.MAIN_THREAD)
    public void onScrolledTop(String s) {
        mAppBarLayout.setExpanded(true);
    }

    private void initView() {
        initAnimation();
        initViewPager();
    }

    private void initAnimation() {
        mOriginalUserAvatar.setImageResource(R.drawable.ali_avator);
        mUserAvatar.setImageResource(R.drawable.ali_avator);

        int[] originalLocation=new int[2]; //头像的原来位置
        int[] location=new int[2]; //头像的现在位置

        final int[] originalWidth = new int[1]; // 头像原来的宽度
        final int[] width = new int[1]; // 头像缩放后的宽度

        // 获取原来头像的位置及宽度
        mOriginalUserAvatar.post(() -> {
            mOriginalUserAvatar.getLocationOnScreen(originalLocation);
            originalWidth[0] =mOriginalUserAvatar.getWidth();
        });

        mUserAvatar.post(() -> {
            mUserAvatar.getLocationOnScreen(location);
            width[0] =mUserAvatar.getWidth();
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int lastOffset; // 头像在这个偏移量范围内执行动画

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                lastOffset=originalLocation[1]-location[1];

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mOriginalUserAvatar.getLayoutParams();

                if (verticalOffset>=0){
                    layoutParams.width= getContext().getResources().getDimensionPixelOffset(R.dimen.size_130);
                    layoutParams.height=getContext().getResources().getDimensionPixelOffset(R.dimen.size_130);
                    mOriginalUserAvatar.setLayoutParams(layoutParams);
                    return;
                }

                float fraction= (float) ((verticalOffset*1.0)/lastOffset*1.0);
                if (lastOffset>-verticalOffset){
                    // 开始执行动画
                    mToolbar.setBackgroundColor(Color.argb((int) (255*(-fraction)), 0, 153, 255));
                    mUserAvatar.setVisibility(View.GONE);

                    float imageWidthOffset=(fraction)*(originalWidth[0]-width[0]);
                    layoutParams.width= (int) (originalWidth[0]+imageWidthOffset);
                    layoutParams.height= (int) (originalWidth[0]+imageWidthOffset);
                    mOriginalUserAvatar.setLayoutParams(layoutParams);

                }else {
                    mUserAvatar.setVisibility(View.VISIBLE);
                    mToolbar.setBackgroundColor(Color.argb(255, 0, 153, 255));
                }
            }
        });
    }

    private void initViewPager() {

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private FragmentManager fragmentManager;

            String[] titles = getContext().getResources().getStringArray(R.array.personal_center_tab);

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return PersonalCenterFragment.newInstance();
                    case 1:
                        return PersonalCenterFragment.newInstance();
                    case 2:
                        return PersonalCenterFragment.newInstance();
                    case 3:
                        return PersonalCenterFragment.newInstance();
                    case 4:
                        return PersonalCenterFragment.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
