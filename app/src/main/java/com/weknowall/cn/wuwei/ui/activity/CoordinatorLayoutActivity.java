package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

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
    @BindView(R.id.personal_center_original_user_avatar_root)
    FrameLayout mAvatorRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        initAnimation();
        initViewPager();
    }

    private void initAnimation() {
        int[] originalLocation=new int[2]; //头像的原来位置
        int[] location=new int[2]; //头像的现在位置

        final int[] originalWidth = new int[1]; // 头像原来的宽度
        final int[] width = new int[1]; // 头像缩放后的宽度

        // 获取原来头像的位置及宽度
        mAvatorRoot.post(() -> {
            mAvatorRoot.getLocationOnScreen(originalLocation);
            originalWidth[0] =mAvatorRoot.getWidth();
        });

        // 获取执行动画之后头像的位置及宽度，因为到达的这个位置是固定的，所以写死
        width[0]= (int) getContext().getResources().getDimension(R.dimen.size_60);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int lastOffset; // 头像在这个偏移量范围内执行动画

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                lastOffset=originalLocation[1];

                if (verticalOffset==0)
                    return;

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mAvatorRoot.getLayoutParams();
                if (lastOffset>-verticalOffset){
                    // 开始执行动画

                    float fraction= (float) ((verticalOffset*1.0)/lastOffset*1.0);
                    float imageWidthOffset=(fraction)*(originalWidth[0]-width[0]);
                    layoutParams.width= (int) (originalWidth[0]+imageWidthOffset);
                    layoutParams.height= (int) (originalWidth[0]+imageWidthOffset);
                    layoutParams.topMargin= (int) (getContext().getResources().getDimensionPixelOffset(R.dimen.size_100)-imageWidthOffset);
                    mAvatorRoot.setLayoutParams(layoutParams);

                    FrameLayout.LayoutParams titleLayoutParams = (FrameLayout.LayoutParams) mTitleRoot.getLayoutParams();
                    titleLayoutParams.topMargin= (int) (getContext().getResources().getDimensionPixelOffset(R.dimen.size_250)+imageWidthOffset);
                    mTitleRoot.setLayoutParams(titleLayoutParams);

                }else {
                    // 通过设置头像距离顶部的距离来实现头像固定到顶部
//                    layoutParams.topMargin=layoutParams.topMargin-(verticalOffset+lastOffset);
//                    mUserAvatar.setLayoutParams(layoutParams);
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
